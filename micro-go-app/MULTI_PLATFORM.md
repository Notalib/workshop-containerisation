# Multi-platform Docker builds

Sometimes you want to build containers for a different architecture than you're running.
E.g. you're on a MacOS (arm64) and want to *also* build a Linux (amd64) container image. Or maybe both?

Here's how to do this with **docker buildx**

## Create a buildx builder container

This is a special docker container that can help you make multi-platform builds.

```bash
# Create the builder
docker buildx create --name multi-builder --use --driver=docker-container
# Bootstrap the builder
docker buildx inspect --bootstrap
```

## Build a cross-platform container image (manifest)

```bash
docker buildx build --platform linux/amd64,linux/arm64 --tag repo/image:tag --push .
```

## Build cross-platform images with docker compose

Add this to the `build:` section of a service:
```yaml
  my-app:
    build:
      context: ./my-app
      platforms:
        - "linux/amd64"
        - "linux/arm64"
```

### Not working?

Maybe docker is not currently using your multi-platform builder.

1. Check registered builders

```bash
docker buildx ls

# Example output:
NAME/NODE                     DRIVER/ENDPOINT       STATUS    BUILDKIT   PLATFORMS
multi-builder                 docker-container
 \_ multi-builder0             \_ rancher-desktop   running   v0.29.0    linux/arm64
...
```

2. Make docker use the builder

```bash
docker buildx use multi-builder
```
