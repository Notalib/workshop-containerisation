# TASK

1. Open the existing `Dockerfile`
2. Fill in the TODOs to copy in the [html](./html) folder to a place nginx will host when run

HINT: It's inside `/use/share/nginx`

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
