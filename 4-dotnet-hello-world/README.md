# TASK

1. Open the existing `Dockerfile`
2. Fill in the TODOs in the first stage, so that the dotnet app is built using the dotnet SDK.
3. Fill in the TODOs in the second stage, so the dotnet is copied over form build stage and starts when the container is run.

## TIPS

### Build

Command to build the container from this folder:
```bash
docker build . --progress=plain --no-cache --tag dotnet-app
```

What do the flags mean?
```
--progress=plain | No fancy colored tty output, just print line-by-line. Helps us not miss anything being printed while we build.
--no-cache | Do not cache any previously completed build steps, always start from the top.
```

### Run

Command to run the built container image
```bash
docker run --rm -it dotnet-app
```
