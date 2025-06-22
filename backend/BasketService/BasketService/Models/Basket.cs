namespace BasketService.Models;
public class BasketItem
{
    public string ProductId { get; set; }

    public int Quantity { get; set; }
}
public class Basket
{
    public string UserId { get; set; }

    public List<BasketItem> Items { get; set; } = new();

    public DateTime UpdatedAt { get; set; } = DateTime.UtcNow;
}