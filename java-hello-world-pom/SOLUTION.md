# Solutions

## Problems with Dockerfile.bad

Problems:
- Huge base image
- No multi-stage build
- Build tools included in runtime
- No layer optimization
- Non-reproducible latest
- Large attack surface
- Slow builds
- No .dockerignore
- Not cloud-friendly

### See the problem

```bash
docker build -f Dockerfile.bad -t hello:bad .
docker build -f Dockerfile.good  -t hello:good  .
docker image ls hello
```

## Improvements in Dockerfile.good

- Separate build and runtime stages
- Dependencies fetched in early layer (cached)

## Run and inject env + volume

Set environment variable and volume-mount a new message into the container at runtime.

```bash
docker build -f Dockerfile.good . --tag java-good
docker run -e "NAME=Daniel" -v ./message.txt:/data/message.txt java-good
```
