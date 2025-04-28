import asyncio
import json
import os
from app.events.price_dropped_event import PriceDroppedEvent
from app.services.email_sender import send_email
from confluent_kafka import Consumer

async def consume():
    conf = {
        'bootstrap.servers': os.getenv('KAFKA_BOOTSTRAP_SERVERS', 'localhost:9092'),
        'group.id': os.getenv('KAFKA_CONSUMER_GROUP_ID', 'notification-service-group'),
        'auto.offset.reset': 'earliest'
    }
    consumer = Consumer(conf)
    consumer.subscribe(['price-drop-events'])  # topic we will create
    
    print("Started Notification Kafka consumer...")

    for msg in consumer:
        if msg is None:
            await asyncio.sleep(1)
            continue
        if msg.error():
            print(f"Consumer error: {msg.error()}")
            continue

        event_data = json.loads(msg.value().decode('utf-8'))
        event = PriceDroppedEvent(**event_data)

        print(f"Received price drop event: {event}")

        email_body = (
            f"Price dropped for {event.productName}!\n"
            f"Old Price: {event.oldPrice}\n"
            f"New Price: {event.newPrice}"
        )
        await send_email(
            to_email="testuser@example.com",
            subject="Price Drop Alert!",
            body=email_body
        )

    consumer.close()
