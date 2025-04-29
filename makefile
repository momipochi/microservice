
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


