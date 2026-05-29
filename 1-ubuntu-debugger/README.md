# TASK

1. Open the existing `Dockerfile`
2. Build and run it as-is, to see what it prints while building AND what it prints when you run it.
   1. Does that reveal something about how containers work for you?
3. Now fill in the TODO, so that our built container image has `curl` and `nslookup` utils available when we start it.

## TIPS

### Build

```bash
docker build . --progress=plain --no-cache --tag ubuntu-debugger
```

What do the flags mean?
```
--progress=plain | No fancy colored tty output, just print line-by-line. Helps us not miss anything being printed while we build.
--no-cache | Do not cache any previously completed build steps, always start from the top.
```

### Run

```bash
docker run --rm -it ubuntu-debugger
```

The flag `--rm` means, remove the container when I'm done with it (detach from its shell).
