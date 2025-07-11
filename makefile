
build:
	docker compose up -d --build

down:
	docker compose down

rebuild: down build

build-s:
	docker compose up -d --build $(SERVICE)
rebuild-s:
	docker compose down
	docker compose up -d --build $(SERVICE)

list:
	docker compose config --services

nexus-rebuild:
	docker-compose -f docker-compose-nexus.yml down
	docker-compose -f docker-compose-nexus.yml up -d --build --no-deps --force-recreate


rebuild-product:
	docker compose down
	docker compose up -d --build product-command-service
	docker compose up -d --build product-query-service
	docker compose up -d --build search-service
	docker compose up -d --build basket-service

rebuild-frontend:
	docker compose -f docker-compose-frontend.yml down
	docker compose -f docker-compose-frontend.yml up -d --build
	