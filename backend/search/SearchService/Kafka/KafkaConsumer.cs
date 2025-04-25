using System.Text.Json;
using System.Text.Json.Serialization;
using Confluent.Kafka;
using Nest;
using SearchService.Models;

namespace SearchService.Kafka;

public class KafkaConsumer : BackgroundService
{
    private readonly ILogger<KafkaConsumer> _logger;
    private readonly IElasticClient _elasticClient;
    private readonly IConsumer<string, string> _consumer;
    private readonly string _bootstrapServers;
    private readonly string _groupId;
    public KafkaConsumer(ILogger<KafkaConsumer> logger, IElasticClient elasticClient, IConfiguration configuration)
    {
        _logger = logger;
        _elasticClient = elasticClient;
        _bootstrapServers = configuration["KAFKA_BOOTSTRAP_SERVERS"] ?? "localhost:9092";
        _groupId = configuration["KAFKA_GROUP_ID"] ?? "product-search-group";
        var config = new ConsumerConfig
        {
            BootstrapServers = _bootstrapServers,
            GroupId = _groupId,
            AutoOffsetReset = AutoOffsetReset.Earliest
        };
        _consumer = new ConsumerBuilder<string, string>(config).Build();
        _consumer.Subscribe("product-created");

    }
    protected override async Task ExecuteAsync(CancellationToken stoppingToken)
    {
        _logger.LogInformation("Kafka consumer started.");

        while (!stoppingToken.IsCancellationRequested)
        {
            await ProcessKafkaMessage(stoppingToken);

        }

    }

    private async Task ProcessKafkaMessage(CancellationToken stoppingToken)
    {
        try
        {
            var consumeResult = _consumer.Consume(stoppingToken);
            if (consumeResult.Message.Value != null)
            {
                var message = JsonSerializer.Deserialize<Product>(consumeResult.Message.Value);
                _logger.LogInformation("Got your message: {Value}, Deserializing...",consumeResult.Message.Value);

                if (message != null)
                {
                    _logger.LogInformation("Indexed product: {Id}",message.Id);
                    await _elasticClient.IndexDocumentAsync(message, stoppingToken);
                }
                else
                {
                    _logger.LogError("Could not deserialize message: {Message}", consumeResult.Message.Value);
                }
            }
            
        }
        catch (ConsumeException e)
        {
            _logger.LogError(e, "Error while consuming Kafka message");
        }
        catch (Exception ex)
        {
            _logger.LogError(ex, "Unexpected error");
        }
    }
    public override void Dispose()
    {
        _consumer?.Close();
        _consumer?.Dispose();
        base.Dispose();
    }
}