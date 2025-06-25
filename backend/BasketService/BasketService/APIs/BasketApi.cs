using System.ComponentModel.DataAnnotations;
using BasketService.Models;
using Microsoft.AspNetCore.Mvc;

namespace BasketService.APIs;

public static class BasketApi
{
    public static WebApplication AddBasketEndpoints(this WebApplication app)
    {
        app.MapGet("/baskets", async ([FromServices] Services.IBasketService service) => await service.GetBaskets());
        app.MapPost("/basket", async ([FromServices] Services.IBasketService service, Basket basket) =>
        {
            var validationResults = new List<ValidationResult>();
            var contextObj = new ValidationContext(basket, serviceProvider: null, items: null);
    
            if (!Validator.TryValidateObject(basket, contextObj, validationResults, validateAllProperties: true))
            {
                var errors = validationResults
                    .GroupBy(v => v.MemberNames.FirstOrDefault() ?? "Unknown")
                    .ToDictionary(
                        g => g.Key,
                        g => g.Select(v => v.ErrorMessage ?? "Invalid value").ToArray()
                    );

                return Results.ValidationProblem(errors);
            }

            await service.UpsertBasket(basket);
            return Results.Ok("Model is valid!");
        });
        return app;
    }
}