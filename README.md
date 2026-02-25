# Exercises for Containerisation Workshop

Create Dockerfiles for these applications

## Pre-requisites

- Install Docker Desktop or Rancher Desktop (OSS)
- Make sure `docker -v` works from your terminal.

## Assignment

1. Create a `Dockerfile` for each app, starting with the lang/tech you feel most comfortable with.
2. Your `Dockerfile` must be split into a **build** stage and a **runner/app** stage.
3. It must start the application when using `docker run`


## Useful commands

### Building
- Build: `docker build <./dir/with/dockerfile> -t <image-tag>` (assumes Dockerfile dir is also build context)
  - Serialized: `DOCKER_BUILDKIT=0 docker build ...` (debug each layer/cmd, by using printed sha as image-tag)
- Re-tag: `docker tag <image-tag> <new-image-tag>`
- Images & their sizes: `docker images`

### Running

- What's running? `docker ps`
- Run: `docker run <image-tag>` (runs interactively until done or ctrl+c)
- Keep running: `docker run --detached <image-tag>` (runs in background until done)
- Debug: `docker run -it --entrypoint /bin/bash <image-tag>`

### 1. ubuntu-debugger

Simple image that extends Ubuntu 24.04 distro with some extra network debugging tools.
Useful to understand base-images and layering

#### TASK

1. Start with `FROM ubuntu24.04` and then make sure curl and dnsutils are installed in the container image.
2. Start and execute into the container image, then try using the `curl` and `nslookup` tools.

### 2. static-web-app

Static nginx-based web-app which hosts static HTML files.

#### TASK

1. Create a static http web-app that hosts our static replacement `index.html` file.
2. You can use `nginx`, `python -m http.server` or any other way to host the static `html/index.html` file via HTTP on an exposed port.

### 3. micro-go-app

Simple scratch-based go app that just outputs the contents of a .txt file

#### TASK

1. This task is purely educational. Inspect the Dockerfile and learn about `scratch`
2. Build the container image and check its disk-size! `docker images | grep <image-tag>`

### java-hello-world

Simple hello-world app in Java

#### TASK
1. Follow Ole's guide on containerizing a Java app

### dotnet-hello-world

Simple hello-world app in .NET 10

Create a Dockerfile for the app and test it!

#### TASK

1. Follow Microsoft's guide on containerizing dotnet apps
2. See https://learn.microsoft.com/en-us/dotnet/core/docker/build-container?tabs=windows&pivots=dotnet-10-0

## Best practices

- Minimize layers by combining related commands.
- Split Dockerfile into build and runtime stages! (massively reduces image size)
- Use .dockerignore to reduce the build-context and image size.
- Pin versions! Use `FROM alpine:3.19.0` rather than just `alpine` or `alpine:latest`.
  - Use of `:stable` tags might be a good idea if available.
- Add metadata to your container images!
    ```
    LABEL org.opencontainers.image.source="https://github.com/org/repo"
    LABEL org.opencontainers.image.version="1.0.0"
    LABEL org.opencontainers.image.description="My custom base image"
    ```


## Bonus assignments

1. Use `docker compose up -d` to build and launch all our containers (in the same virtual network).
2. Try executing into on of the running containers (`docker compose exec -it <service-name> /bin/bash`).
3. Try to fetch our HTML website from `static-web-app` via the `ubuntu-debugger` container.
4. Try fetching logs from our `static-web-app` via cmd `docker compose logs -f <service-name>`, can you see the fetch?
5. Try reducing some of the container image sizes! Check with `docker images`.
