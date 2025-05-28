# Dockerfile

# Stage 1: Build
FROM node:23-slim AS builder

WORKDIR /app

COPY . .

RUN npm install && npm run build

# Stage 2: Serve
FROM nginx:alpine

# Copy built files from stage 1
COPY --from=builder /app/dist /usr/share/nginx/html

# Optional: Replace default Nginx config
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
