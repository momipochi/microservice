using Nest;
using SearchService.Models;

namespace SearchService.Services;

public class ProductSearchService(IElasticClient elastic)
{
    public async Task<IEnumerable<Product>> SearchAsync(string query)
    {
        var result = await elastic.SearchAsync<Product>(s => s
            .Index("products")
            .Query(q => q.Match(m => m.Field(f => f.Name).Query(query)))
        );

        return result.Documents;
    }
}