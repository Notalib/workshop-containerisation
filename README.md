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
