using Nest;
using SearchService.Models;

namespace SearchService.Services;

public class ProductSearchService(IElasticClient elastic)
{
    public record ProductSearchQuery(string Query, int Page = 1, int SizeLimit = 5);

    private readonly int SEARCH_QUERY_SIZE_LIMIT = 5;
    private readonly int SEARCH_LISTINGS_SIZE_LIMIT = 10;
    public async Task<IEnumerable<Product>> SearchAsync(string query)
    {
        return await Search(new ProductSearchQuery(query,SizeLimit: SEARCH_QUERY_SIZE_LIMIT));
    }
    public async Task<IEnumerable<Product>> SearchListingsAsync(string query)
    {
        return await Search(new ProductSearchQuery(query,SizeLimit: SEARCH_LISTINGS_SIZE_LIMIT));
    }

    private async Task<IEnumerable<Product>> Search(ProductSearchQuery queryObject)
    {
        var from = (queryObject.Page - 1) * queryObject.SizeLimit;
        var result = await elastic.SearchAsync<Product>(s => s
            .Index("products")
            .From(from)
            .Size(queryObject.SizeLimit)
            .Query(q => q
                .Wildcard(w => w
                    .Field(f => f.Name) // no .keyword suffix
                    .Value($"*{queryObject.Query.ToLower()}*")
                )
            )
        );

        return result.Documents;
    }

}