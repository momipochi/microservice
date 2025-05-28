using Microsoft.AspNetCore.Http.HttpResults;
using Microsoft.AspNetCore.Mvc;
using SearchService.Services;

namespace SearchService.Api;

public static class ElasticSearch
{
    public static WebApplication AddElasticSearchEndpoints(this WebApplication app)
    {
        app.MapGet("/search",async (string query, [FromServices]ProductSearchService service) => await service.SearchAsync(query));
        app.MapGet("/listing",async (string query, string? order, [FromServices]ProductSearchService service) => await service.SearchListingsAsync(query,order));
        app.MapGet("/test", () =>
        {
            Console.WriteLine("Yep, you hit test endpoint");
            return "Ok(StatusCodes.Status200OK)";
        });
        return app;
    }
}