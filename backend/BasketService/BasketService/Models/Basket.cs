using System.ComponentModel.DataAnnotations;
using MongoDB.Bson.Serialization.Attributes;

namespace BasketService.Models;
public class BasketItem
{
    [Required(ErrorMessage = "ProductId is required")]
    public string ProductId { get; set; }
    
    [Required(ErrorMessage = "Quantity is required")]
    [Range(0,99,ErrorMessage = "Quantity must be within range")]
    public int Quantity { get; set; }
}
public class Basket
{
    [Required(ErrorMessage = "UserId is required")]
    [BsonId]
    public string UserId { get; set; }
    
    public List<BasketItem> Items { get; set; } = new();

    public DateTime UpdatedAt { get; set; } = DateTime.UtcNow;
}