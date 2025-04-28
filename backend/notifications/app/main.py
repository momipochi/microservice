import asyncio
from fastapi import FastAPI
from fastapi.concurrency import asynccontextmanager

from app.services.kafka_consumer import consume

app = FastAPI()

@asynccontextmanager
async def startup_event():
    asyncio.create_task(consume())

@app.get("/health")
async def health_check():
    return {"status": "ok"}