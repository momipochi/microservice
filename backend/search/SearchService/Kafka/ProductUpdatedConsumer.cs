using AutoMapper;
using Elasticsearch.Net;
using Nest;
using SearchService.Events;
using SearchService.Events.Products;
using SearchService.Models;

namespace SearchService.Kafka;

public class ProductUpdatedConsumer : KafkaConsumer<ProductUpdatedEvent>
{
    public ProductUpdatedConsumer(ILogger<KafkaConsumer<ProductUpdatedEvent>> logger, IElasticClient elasticClient, IConfiguration configuration, IMapper mapper) : base(logger, elasticClient, configuration, mapper)
    {
        _consumer.Subscribe(EventTypes.PRODUCT_UPDATED_EVENT);
    }

    protected override async Task ProcessAction(CancellationToken stoppingToken, ProductUpdatedEvent message)
    {
        Product product = _mapper.Map<Product>(message);
        await _elasticClient.IndexAsync(product,i=>i.Index("products").Id(product.Id).Refresh(Refresh.True),stoppingToken);
    }
}