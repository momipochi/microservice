using BasketService.Models;
using BasketService.Repository;

namespace BasketService.Services;

public interface IBasketService
{
    Task<IEnumerable<Basket>> GetBaskets();
    Task UpsertBasket(Basket basket);
}

public class BasketService(IBasketRepository repository) : IBasketService
{
    public async Task<IEnumerable<Basket>> GetBaskets()
    {
        return await repository.GetBaskets();
    }

    public async Task UpsertBasket(Basket basket)
    {
        await repository.UpsertBasket(basket);
    }
}