using Microsoft.AspNetCore.Mvc;

namespace BasketService.APIs;

public static class BasketApi
{
    public static WebApplication AddBasketEndpoints(this WebApplication app)
    {
        app.MapGet("/baskets", async ([FromServices] Services.IBasketService service) => await service.GetBaskets());
        return app;
    }
}