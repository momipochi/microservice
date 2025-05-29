using Nest;
using SearchService.DTO;
using SearchService.Models;

namespace SearchService.Services;

public class ProductSearchService(IElasticClient elastic)
{
    public record ProductSearchQuery(string Query, int Page=1, string OrderBy="name", bool SortDesc = true, int SizeLimit = 5);

    private readonly int SEARCH_QUERY_SIZE_LIMIT = 5;
    private readonly int SEARCH_LISTINGS_SIZE_LIMIT = 10;
    public async Task<IEnumerable<Product>> SearchAsync(string query)
    {
        return await Search(new ProductSearchQuery(query, SizeLimit: SEARCH_QUERY_SIZE_LIMIT));
    }
    public async Task<ProductListingDTO> SearchListingsAsync(string query, string? order, int? page)
    {
        var pageNumber = page ?? 1;

        if (!string.IsNullOrEmpty(order))
        {
            var resultWithoutOrder = await SearchWithTotalHitsTracking(new ProductSearchQuery(query, pageNumber, order,
                SizeLimit: SEARCH_LISTINGS_SIZE_LIMIT));
            return new ProductListingDTO(resultWithoutOrder.TotalCount,pageNumber,resultWithoutOrder.Products);
        }

        var resultWithOrder = await SearchWithTotalHitsTracking(new ProductSearchQuery(query, pageNumber,
            SizeLimit: SEARCH_LISTINGS_SIZE_LIMIT));
        return new ProductListingDTO(resultWithOrder.TotalCount/SEARCH_LISTINGS_SIZE_LIMIT,pageNumber,resultWithOrder.Products);

    }

    private async Task<(IEnumerable<Product> Products, long TotalCount)> SearchWithTotalHitsTracking(ProductSearchQuery queryObject)
    {
        var from = (queryObject.Page - 1) * queryObject.SizeLimit;

        var result = await elastic.SearchAsync<Product>(s =>
            s.Index("products")
                .From(from)
                .Size(queryObject.SizeLimit)
                .TrackTotalHits() 
                .Query(q => q
                    .Wildcard(w => w
                        .Field(f => f.Name)
                        .Value($"*{queryObject.Query.ToLower()}*")
                    )
                )
        );
        return result == null ? ([], 0) : (result.Documents, result.HitsMetadata.Total.Value);
    }
    
    private async Task<IEnumerable<Product>> Search(ProductSearchQuery queryObject)
    {
        var from = (queryObject.Page - 1) * queryObject.SizeLimit;
        var result = await elastic.SearchAsync<Product>(s =>
            {
                var searchDescriptor = s
                    .Index("products")
                    .From(from)
                    .Size(queryObject.SizeLimit)
                    .Query(q => q
                        .Wildcard(w => w
                            .Field(f => f.Name)
                            .Value($"*{queryObject.Query.ToLower()}*")
                        )
                    );
                
                return searchDescriptor;
            }
        );

        return result.Documents;
    }


}