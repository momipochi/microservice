from pydantic import BaseModel
from decimal import Decimal

class PriceDroppedEvent(BaseModel):
    productId: int
    productName: str
    oldPrice: Decimal
    newPrice: Decimal
