# See https://learn.microsoft.com/en-us/dotnet/core/docker/build-container?tabs=windows&pivots=dotnet-10-0

#################
## Build stage ##
#################

FROM mcr.microsoft.com/dotnet/sdk:10.0 AS builder

WORKDIR /src

###################
## Runtime stage ##
###################

FROM mcr.microsoft.com/dotnet/runtime:10.0-alpine AS runtime

LABEL org.opencontainers.image.source=https://github.com/notalib/workshop-containerisation

# Only for alpine runtime
RUN apk update  && \
    apk upgrade --no-cache

# Only for the debian-based runtime
# RUN apt update && \
#     apt upgrade -y && \
#     apt install -y locales locales-all && \
#     rm -rf /var/lib/apt/lists/*

# To avoid .NET Culture issues, define Danish locale.
ENV LANG="en_DK.UTF-8"
ENV LC_ALL="en_DK.UTF-8"

COPY --from=builder /built /app

# Set dotnet environment and ASP.NET web exposure.
ENV DOTNET_ENVIRONMENT="Development"
#ENV ASPNETCORE_URLS="http://*:5000"

# Use the dotnet non-root user, can be changed via env variable
USER $APP_UID

WORKDIR /app

ENTRYPOINT [ "dotnet", "hello-world.dll" ]
