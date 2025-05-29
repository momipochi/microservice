using Nest;
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
    public async Task<IEnumerable<Product>> SearchListingsAsync(string query, string? order, int? page)
    {
        var pageNumber = page ?? 1;

        if (!string.IsNullOrEmpty(order))
        {
            return await Search(new ProductSearchQuery(query, pageNumber ,order ,SizeLimit: SEARCH_LISTINGS_SIZE_LIMIT));
        }
        return await Search(new ProductSearchQuery(query, pageNumber, SizeLimit: SEARCH_LISTINGS_SIZE_LIMIT));
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