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

# Futher Reading

Currently this project aims to create a fully functional, enterprise ready microservice. The application runs only on docker at the moment, in the future I plan to add **Kubernetes** to the tech stack, which should be simple as every service was built with **Kubernetes** in mind, it is for now only on docker for the sake of simplicity.

Observability is also something missing in the current porject, however, the reason for this is once again, for simplicity, and I do plan to add it to the tech stack in the future. It doesn't make sense at the moment to integrate so many softwares together.

At some point, I would like to also implement Service Mesh architecture. Solutions like **Consul** exist, however, the last time I tried it seemed to not work, so I might have to implement my own using for example Envoy Proxy, which is what **Consul** uses under the hood.
