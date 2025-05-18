# Introduction

Repo for microservice project. This project is intended to learn about distributed system, from scratch. Implementing distributed system concepts and design patterns.

This project contains common softwares used in enterprise companies that create a scalable distributed system.
As well as many microservice architecture concepts and design patterns such as **DDD (Domain Driven Design)**, **CQRS (Command Query Responsibility Segregation)**, **Event sourcing**, etc.

---

# Current Tech Stack

- Elasticsearch
- Docker
- ReactTS
- Spring
- ASP\.NET
- Postgres
- MongoDB
- Kafka
- Nexus
- ...more later

---

# Current design patterns

- CQRS (Command query responsibility segregation)
- Event driven architecture
- DDD (Domain driven design)
- Event sourcing
- ...more later

---

# Design Intentions

This project, for now, intentionally skips certain designs. `Kubernetes` is now not in the tech stack primarily because this project is meant be only a demo, however, it is designed with scalability in mind, every service is dockerized. Observability tools such as `Jaeger`, `Prometheus` and `Grafana` are also not in this project currently, the reason for this is because setting up these tools are time consuming. `Kubernetes` and other tools that are missing in this project are also primarily due to the time it takes to setup.

Aside from tools, common microservice design patterns are, likewise, not implemented, such as Saga Pattern. The reason for this is the same as the tools mentioned above, some of these concepts are simply quite time consuming, they will be added in the future. If there were to be implemented right now as the project grows, it quickly becomes a mess that is hard to navigate.
