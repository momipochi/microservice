using Nest;
using SearchService.Events.Products;
using SearchService.Kafka;

namespace SearchService.Extensions;

public static class Extensions
{
    public static IServiceCollection AddKafkaConfigs(this IServiceCollection services)
    {
        services.AddHostedService<ProductCreatedConsumer>();
        services.AddHostedService<ProductUpdatedConsumer>();
        return services;
    }
    public static IServiceCollection AddElasticConfigs(this IServiceCollection services, IConfiguration configuration)
    {
        var elasticUri = configuration["ELASTICSEARCH_URI"] ?? "http://localhost:9200";

        var settings = new ConnectionSettings(new Uri(elasticUri))
            .DefaultIndex("products");

        services.AddSingleton<IElasticClient>(new ElasticClient(settings));
        return services;
    }
    public static IServiceCollection AddCorsConfig(this IServiceCollection services)
    {
        services.AddCors(options =>
        {
            options.AddPolicy("AllowLocalhost",
                builder => builder.WithOrigins("http://localhost:3000", "http://localhost:5173")
                    .AllowAnyHeader()
                    .AllowAnyMethod());
        });
        return services;
    }
}