# Exercises for Containerisation Workshop

Create Dockerfiles for these applications

## Pre-requisites

- Install **Rancher Desktop (Recommended!)**
  - Make sure `~/.rd/bin` is on your PATH!
  - If already using Docker Desktop or Podman, follow our network guidelines [here](https://kb-dk.atlassian.net/wiki/spaces/CT/pages/836009988/Docker+setup).
- Make sure `docker --version` works from your terminal.

## Docs

Have these documentation sites in hand when doing the assignments:
- https://docs.docker.com/get-started/docker-overview/
- https://docs.docker.com/reference/dockerfile/
- https://docs.docker.com/guides/java/containerize/
- https://docs.docker.com/guides/dotnet/containerize/

## Repo layout

- `<num>-<name>/` — apps for **you** to containerize. Work through these as exercises.
- `edu-<name>/` — already-containerized educational examples. Read, run, learn from them.
- [cli-demo/](./cli-demo/) — live-demo walkthrough showing the power of the Docker CLI & Compose.

## Assignments

1. Containerize each app in the numbered folders, starting with the lang/tech you feel most comfortable with. See TASKS further down in this readme.
2. Try to first create a single-stage `Dockerfile` to build and run the app.
  - It must automatically start the application when using `docker run <image-name>`
3. Then split your `Dockerfile` into a **build** stage and a **runner/app** stage.

### Solutions

If you're completely stuck or just want to compare, solutions can be found on the `solutions` branch.

```bash
git checkout -t origin/solutions
```

## Useful commands

### Building
- Build: `docker build <./context/dir> --file ./path/to/Dockerfile --tag <name:tag>`
  - Serialized: `DOCKER_BUILDKIT=0 docker build ...` (debug each layer/cmd, by using printed sha as image-tag)
- Images & their sizes:
  - Show local repo: `docker image ls`
  - Inspect an image: `docker image inspect <name:tag>`
- Re-tag: `docker tag <source-name:tag> <dest-name:tag>`

### Running

- What's running? `docker ps`
- Run: `docker run <image-tag>` (runs interactively until done or ctrl+c)
- Keep running: `docker run --detached <image-tag>` (runs in background until done)
- Debug: `docker run --interactive --tty --entrypoint /bin/bash <image-tag>`

### [1-ubuntu-debugger](./1-ubuntu-debugger/)

Simple image that extends Ubuntu 24.04 distro with some extra network debugging tools.
Useful to understand base-images and layering

#### TASK

1. Start with `FROM ubuntu:24.04` and then make sure curl and dnsutils are installed in the container image.
2. Start and execute into the container image, then try using the `curl` and `nslookup` tools.

### [2-java-hello-world](./2-java-hello-world/)

Simple hello-world app in Java.

#### TASK
1. Containerize this little Java hello-world app, make it start the Hello.java file.
2. Run the container image and change who it says hello to at runtime.
3. Make the container print the contents of a local file by volume mapping it into the container.

### [3-java-hello-world-pom](./3-java-hello-world-pom/)

Maven-based Java hello-world app.

#### TASK
1. Containerize the maven-based Java hello-world app.
2. Run the container image and change who it says hello to at runtime.
3. Make the container print the contents of a local file by volume mapping it into the container.

### [4-dotnet-hello-world](./4-dotnet-hello-world/)

Simple hello-world app in .NET 10.

Create a Dockerfile for the app and test it!

#### TASK

1. Follow Microsoft's guide on containerizing dotnet apps
2. See https://learn.microsoft.com/en-us/dotnet/core/docker/build-container?tabs=windows&pivots=dotnet-10-0

### [5-static-web-app](./5-static-web-app/)

Static nginx-based web-app which hosts static HTML files.

#### TASK

1. Create a static http web-app that hosts our static replacement `index.html` file.
2. You can use `nginx`, `python -m http.server` or any other way to host the static `html/index.html` file via HTTP on an exposed port.

## Educational examples (already containerized)

These are not exercises — they're ready-to-run examples to study and play with.

### [edu-micro-go-app](./edu-micro-go-app/)

A `scratch`-based Go app that prints the contents of a `.txt` file. Inspect the Dockerfile to see how multi-stage + `FROM scratch` produces tiny images. Build it and check the image size with `docker images`.

### [edu-distroless](./edu-distroless/)

A distroless Go app with a walkthrough on how to debug containers that have no shell (using `cdebug`, `netshoot`, etc.).

### [edu-spring-postgres](./edu-spring-postgres/)

A full Spring Boot + Postgres stack defined entirely with Docker Compose — no local JDK or database needed. Demonstrates multi-service Compose, networking, and volume persistence.

## Best practices

- Minimize layers by combining related commands.
- Split Dockerfile into build and runtime stages! (massively reduces image size)
- Use .dockerignore to reduce the build-context and image size.
- Pin base image versions — avoid `alpine` or `alpine:latest`. From least to most strict:
  - **Major/minor tag** (e.g. `eclipse-temurin:21-jre`, `nginx:stable`) — fine for workshops and dev; you still get security patches.
  - **Full version tag** (e.g. `alpine:3.19.0`) — recommended for production.
  - **Digest pin** (e.g. `alpine@sha256:...`) — fully reproducible builds; pair with a tool like Renovate/Dependabot to keep it updated.
- Add metadata to your container images!
    ```
    LABEL org.opencontainers.image.source="https://github.com/org/repo"
    LABEL org.opencontainers.image.version="1.0.0"
    LABEL org.opencontainers.image.description="My awesome app image"
    ```

## Bonus assignments

1. Use `docker compose up --detach` to build and launch all our containers (in the same virtual network).
2. Try executing into on of the running containers (`docker compose exec -it <service-name> /bin/bash`).
3. Try to fetch our HTML website from `static-web-app` via the `ubuntu-debugger` container.
4. Try fetching logs from our `static-web-app` via cmd `docker compose logs --follow <service-name>`, can you see when it was fetched?
5. Try reducing some of the container image sizes! Check size with `docker images`.
