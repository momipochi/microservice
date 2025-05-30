﻿using System.Text.Json.Serialization;

namespace SearchService.Models;

public class Product
{
    [JsonPropertyName("id")]
    public Guid Id { get; set; } 
    [JsonPropertyName("name")]

    public string Name { get; set; } = null!;
    [JsonPropertyName("description")]

    public string Description { get; set; } = null!;
    [JsonPropertyName("price")]

    public decimal Price { get; set; }
}