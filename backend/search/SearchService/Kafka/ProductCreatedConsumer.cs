using AutoMapper;
using Nest;
using SearchService.Events;
using SearchService.Events.Products;
using SearchService.Models;

namespace SearchService.Kafka;

public class ProductCreatedConsumer : KafkaConsumer<ProductCreatedEvent>
{
    public ProductCreatedConsumer(ILogger<KafkaConsumer<ProductCreatedEvent>> logger, IElasticClient elasticClient, IConfiguration configuration, IMapper mapper) : base(logger, elasticClient, configuration, mapper)
    {
        _consumer.Subscribe(EventTypes.PRODUCT_CREATED_EVENT);
    }

    protected override async Task ProcessAction(CancellationToken stoppingToken, ProductCreatedEvent message)
    {
        Product product = _mapper.Map<Product>(message);
        await _elasticClient.IndexDocumentAsync(product, stoppingToken);
    }
}