using SearchService.Models;

namespace SearchService.DTO;

public record ProductListingDTO(long pages,int currentPage, IEnumerable<Product> products);