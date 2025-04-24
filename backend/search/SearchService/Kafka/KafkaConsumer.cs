using System.Text.Json;
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
            ProcessKafkaMessage(stoppingToken);

            await Task.Delay(TimeSpan.FromMinutes(1), stoppingToken);
        }

        _consumer.Close();
    }

    private void ProcessKafkaMessage(CancellationToken stoppingToken)
    {
        try
        {
            var consumeResult = _consumer.Consume(stoppingToken);

            var message = consumeResult.Message.Value;

            _logger.LogInformation($"Received product update: {message}");
        }
        catch (Exception ex)
        {
            _logger.LogError($"Error processing Kafka message: {ex.Message}");
        }
    }
    public override void Dispose()
    {
        _consumer?.Close();
        _consumer?.Dispose();
        base.Dispose();
    }
}