using BasketService.Models;
using BasketService.Repository;

namespace BasketService.Services;

public interface IBasketService
{
    Task<IEnumerable<Basket>> GetBaskets();
}

public class BasketService(IBasketRepository repository) : IBasketService
{
    public async Task<IEnumerable<Basket>> GetBaskets()
    {
        return await repository.GetBaskets();
    }
}