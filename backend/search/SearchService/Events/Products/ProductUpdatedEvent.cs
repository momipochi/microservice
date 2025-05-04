using System.Text.Json.Serialization;

namespace SearchService.Events.Products;

public class ProductUpdatedEvent
{
    [JsonPropertyName("id")] 
    public Guid Id { get; set; } = Guid.Empty; 
    [JsonPropertyName("name")]

    public string Name { get; set; } = null!;
    [JsonPropertyName("description")]

    public string Description { get; set; } = null!;
    [JsonPropertyName("price")]

    public decimal Price { get; set; }
}