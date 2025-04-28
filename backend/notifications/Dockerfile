# Use official lightweight Python image
FROM python:alpine

# Set working directory

RUN apk --no-cache upgrade \
    && pip install --upgrade pip \
    && apk --no-cache add tzdata build-base gcc libc-dev g++ make git bash

RUN git clone https://github.com/edenhill/librdkafka.git && cd librdkafka \
    && git checkout tags/v2.2.0 && ./configure --clean \
    && ./configure --prefix /usr/local \
    && make && make install

RUN pip install confluent-kafka==2.2.0
# Install pipreqs
RUN pip install pipreqs

# Copy the entire app
COPY ./app ./app


# Generate requirements.txt automatically
RUN pipreqs ./app

# Install dependencies
RUN pip install -r ./app/requirements.txt

RUN pip install uvicorn
RUN pip install fastapi

# Expose the application port (optional)
EXPOSE 8001

# Run the FastAPI app with Uvicorn
CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "8001"]
