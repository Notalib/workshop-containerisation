# Containerisation CLI Demo

A quick live-demo walkthrough showing the power of containers using Docker CLI and Docker Compose.

---

# 1. Instant Web Server

Start an NGINX web server in seconds:

```bash
docker run --detach --publish 8080:80 --name web nginx
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

Confirm it's up and accepting connections:

```bash
docker logs db
telnet 127.0.0.1 5432
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
:information_source: -it is short for --interactive --tty

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
# NOTE: eclipse-temurin = OpenJDK binaries
docker run --rm eclipse-temurin:17 java -version
docker run --rm eclipse-temurin:21 java -version
docker run --rm eclipse-temurin:latest java -version
```

## Observations

- Multiple versions simultaneously
- No dependency conflicts
- Perfect reproducibility

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

## BONUS: Can we persist our infrastructure commands?

These Docker commands setup our infrastructure consistently, but wouldn't it be nice if we could persist these as a configuration file? This is where **Docker Compose** comes in!

The commands above have been persisted as [compose.yml](./compose.yml), and can all be brought up with a simple `docker compose up --detach` command.

This is called :star2: Infrastructure as Code :star2:

---

# 6. Docker Compose Example

## Official Spring Boot + Postgres Sample (modified)

A full JVM application + database stack — no local JDK, no local Postgres, just one command.

This is a modified version of spring-postgres example from [compose-awesome.](https://github.com/docker/awesome-compose).

Entire system/stack is defined in: [spring-postgres/compose.yml](./spring-postgres/compose.yaml)

This sample is specifically built around:
- Spring Boot
- PostgreSQL
- Docker Compose
- Container networking
- Persistence wiring (data survives container deletion)

## Additions by ddfreiling
- Added /new form to add greeting
- Added /greetings endpoint to show all greetings.

## Commands
:magic_wand: Start the entire stack
```bash
docker compose up --detach --build
```

:pencil: Add a greeting via the `/new` form at [http://localhost:8080/new](http://localhost:8080/new), then peek at the actual table inside the running database container:
```bash
docker compose exec db psql -U postgres -d example -c "SELECT * FROM greetings;"
```

:stop_sign: Stop the stack
```bash
docker compose stop
```

:boom: Delete it *all*
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

---

# 7. Volume mounting

We can mount in volumes using the CLI with the `--volume` or `-v` argument.

Volumes can be either relative or absolute paths on your machine - or the name of a docker volume.

Example:
```bash
docker run --volume ./web:/usr/share/nginx/html --detach --publish 8080:80 --name web nginx
```

---

# 8. Cleanup

Remove containers:

```bash
docker rm --force web db wp wp-db spring-app spring-db
```

Remove networks:

```bash
docker network rm wp-net spring-net
```

Remove compose stack (and data volumes):

```bash
docker compose down --volumes
```

General cleanup of all unused Docker resources
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
