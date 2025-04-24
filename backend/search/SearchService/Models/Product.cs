namespace SearchService.Models;

public class Product
{
    public int Id { get; set; } = 0;
    public string Name { get; set; } = null!;
    public string Description { get; set; } = null!;
    public decimal Price { get; set; }
}