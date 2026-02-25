# Let's create a simple container that hosts a static HTML site via HTTP on a given port
FROM nginx:stable

COPY html /usr/share/nginx/html

# If not defined, we inherit EXPOSE from base image
# EXPOSE 80

# If not defined, we inherit ENTRYPOINT from base image
# ENTRYPOINT [ "nginx" ]
