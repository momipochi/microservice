using BasketService.Configs;
using BasketService.Models;
using Microsoft.Extensions.Options;
using MongoDB.Driver;

namespace BasketService.Repository;

public interface IBasketRepository
{
    Task<Basket> GetBasket(string id);
    Task<IEnumerable<Basket>> GetBaskets();
    Task UpsertBasket(Basket basket);
    Task RemoveProductFromBasket(string userId, string productId);
}

public class BasketRepository : IBasketRepository
{
    private readonly IMongoCollection<Basket> _baskets;

    public BasketRepository(IOptions<BasketDatabaseSettings> settings)
    {
        var client = new MongoClient(settings.Value.ConnectionString);
        var database = client.GetDatabase(settings.Value.DatabaseName);
        _baskets = database.GetCollection<Basket>(settings.Value.BasketsCollectionName);
    }

    public async Task<Basket> GetBasket(string id)
    {
        return await _baskets.Find(i=>i.UserId == id).FirstOrDefaultAsync();
    }

    public async Task<IEnumerable<Basket>> GetBaskets()
    {
        var cursor = await _baskets.FindAsync(FilterDefinition<Basket>.Empty);
        return await cursor.ToListAsync();
    }

    public async Task UpsertBasket(Basket basket)
    {
        await _baskets.ReplaceOneAsync(
            b => b.UserId == basket.UserId,
            basket,
            new ReplaceOptions { IsUpsert = true });

    }

    public async Task RemoveProductFromBasket(string userId, string productId)
    {
        var update = Builders<Basket>.Update.PullFilter(
            b => b.Items, item => item.ProductId == productId
        );
        
        await _baskets.UpdateOneAsync(
            b => b.UserId == userId,
            update
        );
    }
}