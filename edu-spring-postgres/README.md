# Spring Boot + Postgres (Docker Compose)

A full JVM application + database stack — no local JDK, no local Postgres, just one command.

Modified from the official [spring-postgres example](https://github.com/docker/awesome-compose) in `awesome-compose`.

The entire system/stack is defined in [compose.yaml](./compose.yaml).

This sample demonstrates:
- Spring Boot
- PostgreSQL
- Docker Compose
- Container networking
- Persistence wiring (data survives container deletion)

The compose file defines two services: `backend` and `db`.

- **Port mapping** — `8888:8080` maps host port **8888** to container port **8080** (where Spring Boot listens). Visit the app at [http://localhost:8888](http://localhost:8888).
- **Service discovery** — the backend connects to Postgres at hostname `db` (just the service name). Compose puts both services on the same network and gives each one DNS, so no IPs or links are needed.

## Additions by ddfreiling
- Added `/new` form to add a greeting
- Added `/greetings` endpoint to show all greetings

## Commands

Start the entire stack:
```bash
docker compose up --detach --build
```

Add a greeting via the `/new` form at [http://localhost:8888/new](http://localhost:8888/new), then peek at the actual table inside the running database container:
```bash
docker compose exec db psql -U postgres -d example -c "SELECT * FROM greetings;"
```

Stop the stack:
```bash
docker compose stop
```

Delete it *all* (including data volumes):
```bash
docker compose down --volumes
```

## Questions
- What happened to the data? Did your greetings survive?

## Observations

- Entire system stack defined as code
- One command deployment
- Application + infrastructure integration
- Environment-driven configuration
- No local JDK or database required
- Automatic networking
- Portable infrastructure
