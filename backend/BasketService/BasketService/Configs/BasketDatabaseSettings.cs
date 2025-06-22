namespace BasketService.Configs;

public class BasketDatabaseSettings
{
    public string ConnectionString { get; set; } = null!;

    public string DatabaseName { get; set; } = null!;

    public string BasketsCollectionName { get; set; } = null!;
}