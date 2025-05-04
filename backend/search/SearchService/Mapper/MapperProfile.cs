using AutoMapper;
using SearchService.Events.Products;
using SearchService.Models;

namespace SearchService.Mapper;

public class MapperProfile:Profile
{
    public MapperProfile()
    {
        CreateMap<ProductCreatedEvent, Product>().ForMember(dest=>dest.Description,src=>src.MapFrom(x=>x.Description)).ForMember(dest=>dest.Name,src=>src.MapFrom(x=>x.Name)).ForMember(dest=>dest.Price,src=>src.MapFrom(x=>x.Price));        
        CreateMap<ProductUpdatedEvent, Product>().ForMember(dest=>dest.Description,src=>src.MapFrom(x=>x.Description)).ForMember(dest=>dest.Name,src=>src.MapFrom(x=>x.Name)).ForMember(dest=>dest.Price,src=>src.MapFrom(x=>x.Price));
    }
}