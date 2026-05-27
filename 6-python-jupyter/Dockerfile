# TODO: Pick a Python base image
# Suggestion:
#   python: https://hub.docker.com/_/python#how-to-use-this-image
FROM ...

WORKDIR /app

# TODO: COPY requirements.txt and install the pinned dependencies with pip
#       Hint: --no-cache-dir keeps the image smaller

# TODO: COPY the notebook(s) and data into /app

COPY data/sales.csv /data/sales.csv

# TODO: EXPOSE the Jupyter port (default 8888)

# TODO: Set the ENTRYPOINT to start Jupyter Lab so it's reachable from your host browser.
# This can be a pain to get right, so feel free to check Dockerfile.solution
#       Hints:
#         - bind to 0.0.0.0 (containers can't see localhost from the host)
#         - disable the browser auto-open
#         - set a fixed token so you don't have to dig it out of the container logs
