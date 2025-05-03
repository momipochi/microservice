
build:
	docker compose up -d --build

down:
	docker compose down

rebuild: down build

rebuild-s:
	docker compose down
	docker compose up -d --build $(SERVICE)

list:
	docker compose config --services

nexus-rebuild:
	docker-compose -f docker-compose-nexus.yml down
	docker-compose -f docker-compose-nexus.yml up -d
jfrog-rebuild:
	docker-compose -f docker-compose-jfrog.yml down
	docker-compose -f docker-compose-jfrog.yml up -d