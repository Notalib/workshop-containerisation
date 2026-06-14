# Let's create a simple container that hosts a static HTML site via HTTP on a given port
FROM nginx:stable

COPY html /usr/share/nginx/html

# If not defined, we inherit EXPOSE from base image
EXPOSE 80

LABEL org.opencontainers.image.source=https://github.com/notalib/workshop-containerisation
LABEL org.opencontainers.image.description="Static web-app built on nginx"

# If not defined, we inherit ENTRYPOINT from base image
# ENTRYPOINT [ "nginx" ]
