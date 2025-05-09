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
builder.Services.AddCorsConfig();
var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.MapOpenApi();
}

app.UseCors("AllowLocalhost");
app.UseHttpsRedirection();


app.AddElasticSearchEndpoints();
app.Run();

