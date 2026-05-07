# Containerisation CLI Demo

A quick live-demo walkthrough showing the power of containers using Docker CLI and Docker Compose.

---

# 1. Instant Web Server

Start an NGINX web server in seconds:

```bash
docker run -d -p 8080:80 --name web nginx
```

Open in browser:

```text
http://localhost:8080
```

## Observations

- No installation required
- No config files
- No service management
- Works instantly on a clean machine

---

# 2. PostgreSQL Database in Seconds

Start PostgreSQL:

```bash
docker run -d \
  -e POSTGRES_PASSWORD=secret \
  -p 5432:5432 \
  --name db \
  postgres
```

Connect to it:

```bash
docker exec -it db psql -U postgres
```

Inside `psql`:

```sql
CREATE TABLE demo (
    id SERIAL PRIMARY KEY,
    name TEXT
);

INSERT INTO demo (name) VALUES ('containers are cool');

SELECT * FROM demo;
```

## Observations

- Fully initialized database in seconds
- No apt install or manual setup
- Disposable infrastructure

---

# 3. Throwaway Runtimes (Node, Python, JDK)

Run a temporary Node.js environment:

```bash
docker run --rm -it node node
```

Inside Node REPL:

```javascript
console.log("Hello from a clean runtime");
[1,2,3].map(x => x * 2)
```

Exit the container:

```javascript
.exit
```

## Observations

- No Node.js installed locally
- No environment pollution
- Disposable tooling

---

# 4. Multiple Runtime Versions Side-by-Side


## Python
```bash
docker run --rm python:3.8 python --version
docker run --rm python:3.12 python --version
```

## Multiple JDK versions side-by-side
```bash
docker run --rm openjdk:17 java -version
docker run --rm openjdk:21 java -version
```

## Observations

- Multiple versions simultaneously
- No dependency conflicts
- Perfect reproducibility

---

# 5. Spring Boot app in one command

```bash
docker run -p 8080:8080 springio/gs-spring-boot-docker
```

---

# 5. WordPress + Database

## Create Network

```bash
docker network create wp-net
```

## Start MySQL

```bash
docker run -d \
  --name wp-db \
  --network wp-net \
  -e MYSQL_ROOT_PASSWORD=secret \
  -e MYSQL_DATABASE=wordpress \
  -e MYSQL_USER=wp \
  -e MYSQL_PASSWORD=wp \
  mysql:8
```

## Start WordPress

```bash
docker run -d \
  --name wp \
  --network wp-net \
  -p 8081:80 \
  -e WORDPRESS_DB_HOST=wp-db:3306 \
  -e WORDPRESS_DB_USER=wp \
  -e WORDPRESS_DB_PASSWORD=wp \
  -e WORDPRESS_DB_NAME=wordpress \
  wordpress
```

Open in browser:

```text
http://localhost:8081
```

## Observations

- Multiple services connected instantly
- Automatic service discovery
- Environment-based configuration
- No manual database wiring

---

# 6. Docker Compose Example

## Official Spring Boot + Postgres Sample (modified)

This is a modified version of spring-postgres example from [compose-awesome.](https://github.com/docker/awesome-compose).

Entire system/stack is defined in: [compose.yml](./compose.yml)

This sample is specifically built around:
- Spring Boot
- PostgreSQL
- Docker Compose
- Container networking
- Persistence wiring (data survives re-start & re-create)

## Additions
- Added /new form to add greeting
- Added /greetings endpoint to show all greetings.


## Commands
Start the entire stack:
```bash
docker compose up --detach --build
```

Stop the stack:
```bash
docker compose down [--volumes]
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

---

# 7. Cleanup

Remove containers:

```bash
docker rm -f web db wp wp-db spring-app spring-db
```

Remove networks:

```bash
docker network rm wp-net spring-net
```

Remove compose stack (and data volumes):

```bash
docker compose down -v
```

General cleanup of all Docker resources
```bash
docker system prune
```

## Observations

- No leftover configuration
- No config drift
- Fully disposable environments

---

# 8. Key Takeaways

- Containers package the entire runtime environment
- Same image runs everywhere
- No "works on my machine"
- Fast local development setup
- Easy reproducibility
- Infrastructure becomes disposable
- Consistent deployments across environments
