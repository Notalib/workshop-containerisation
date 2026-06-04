# TASK

1. Open the existing `Dockerfile`
2. Fill in the TODOs to copy in the [html](./html) folder to a place nginx will host when run

HINT: It's inside `/usr/share/nginx`

## TIPS

### Build

Command to build the container from this folder:
```bash
docker build . --progress=plain --no-cache --tag static-web
```

What do the flags mean?
```
--progress=plain | No fancy colored tty output, just print line-by-line. Helps us not miss anything being printed while we build.
--no-cache | Do not cache any previously completed build steps, always start from the top.
```

### Run

Command to run the built container image and expose it on your host port 8888.
```bash
docker run -d -p 8888:80 static-web
```

Now try opening `localhost:8888` in your browser.

### Runtime volume mounting

Alternatively we can just mount the `html` dir at runtime on the unmodified nginx image:

```bash
docker run --rm -p 8888:80 -v ./html:/usr/share/nginx/html --name static-web nginx
```
