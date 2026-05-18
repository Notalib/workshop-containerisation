# Distroless container images

## Build & Start

Build and start the distroless container like this:
```bash
# Build and tag
docker build . --tag go-sleep

# Start the container with alias "distroless"
docker run -d --name distroless go-sleep

# Check the logs
docker logs -f distroless
```

## Debugging tool

Make sure you have a debugging tool installed
```bash
brew install cdebug

cdebug exec -it distroless sh
```

You should now be inside the same isolation as the distroless container
Inspect its filesystem and running processes!

```bash
find /app
ps aux
lsof -p 1
strace -p 1
# Inspect port usage
netstat -tulpn
# Network debugging tools
nslookup my-service.kb.dk
wget google.com
```

## Manual debugging

You can also manually run any image with the same isolation as a running container. This however doesn't mount the container's filesystem into the debug container for you.

```bash
docker run --rm -it --pid=container:yourapp --net=container:yourapp nicolaka/netshoot
```

netshoot can be a powerful debugging image. It also contains ss, strace and tcpdump.

### Mounting running container filesystem

If you want to also mount the filesystem of the running container into netshoot, first find the container's mountpoint:
```bash
docker inspect <yourapp> --format '{{.GraphDriver.Data.MergedDir}}'
```

Then run netshoot with that layer mounted into the container:
```bash
docker run --rm -it \
  --pid=container:<yourapp> \
  --net=container:<yourapp> \
  -v /var/lib/docker/overlay2/<ID>/merged:/mnt/rootfs \
  nicolaka/netshoot
```

Then from within netshoot mount it:
```bash
ls /mnt/rootfs
chroot /mnt/rootfs sh   # if you really want to go full deep dive
```

(Note: path differs depending on storage driver and OS — this is why it’s not a recommended approach.)

## Teardown

Stop and remove the distroless container

```bash
docker rm -f distroless
```
