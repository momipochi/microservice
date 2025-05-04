using SearchService.Api;
using SearchService.Extensions;
using SearchService.Services;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
// Learn more about configuring OpenAPI at https://aka.ms/aspnet/openapi
builder.Services.AddOpenApi();

builder.Services.AddKafkaConfigs();
builder.Services.AddElasticConfigs(builder.Configuration);
builder.Services.AddScoped<ProductSearchService>();
builder.Services.AddAutoMapper(typeof(Program));

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.MapOpenApi();
}

app.UseHttpsRedirection();


app.AddElasticSearchEndpoints();
app.Run();

