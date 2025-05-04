using System.Text.Json;
using System.Text.Json.Serialization;
using AutoMapper;
using Confluent.Kafka;
using Nest;
using SearchService.Models;

namespace SearchService.Kafka;

public abstract class KafkaConsumer<T> : BackgroundService where T:class
{
    protected readonly IMapper _mapper;
    protected readonly ILogger<KafkaConsumer<T>> _logger;
    protected readonly IElasticClient _elasticClient;
    protected readonly IConsumer<string, string> _consumer;
    private readonly string _bootstrapServers;
    private readonly string _groupId;

    protected KafkaConsumer(ILogger<KafkaConsumer<T>> logger, IElasticClient elasticClient, IConfiguration configuration, IMapper mapper)
    {
        _logger = logger;
        _elasticClient = elasticClient;
        _mapper = mapper;
        _bootstrapServers = configuration["KAFKA_BOOTSTRAP_SERVERS"] ?? "localhost:9092";
        _groupId = configuration["KAFKA_GROUP_ID"] ?? "product-search-group";
        var config = new ConsumerConfig
        {
            BootstrapServers = _bootstrapServers,
            GroupId = _groupId,
            AutoOffsetReset = AutoOffsetReset.Earliest
        };
        _consumer = new ConsumerBuilder<string, string>(config).Build();

    }
    protected override async Task ExecuteAsync(CancellationToken stoppingToken)
    {
        await Task.Run(async () =>
        {
            _logger.LogInformation("Kafka consumer started.");

            while (!stoppingToken.IsCancellationRequested)
            {
                await ProcessKafkaMessage(stoppingToken);
            }
        },stoppingToken);

    }

    protected abstract Task ProcessAction(CancellationToken stoppingToken, T message);
    private async Task ProcessKafkaMessage(CancellationToken stoppingToken) 
    {
        try
        {
            var consumeResult = _consumer.Consume(stoppingToken);
            if (consumeResult.Message.Value != null)
            {
                var message = JsonSerializer.Deserialize<T>(consumeResult.Message.Value);
                _logger.LogInformation("Got your message: {Value}, Deserializing...",consumeResult.Message.Value);

                if (message != null)
                {
                    _logger.LogInformation("Indexed record: {data}",message.ToString());
                    await ProcessAction(stoppingToken, message);
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