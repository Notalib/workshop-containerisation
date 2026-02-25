# See https://learn.microsoft.com/en-us/dotnet/core/docker/build-container?tabs=windows&pivots=dotnet-10-0

#################
## Build stage ##
#################

FROM mcr.microsoft.com/dotnet/sdk:10.0 AS builder

WORKDIR /src

COPY hello-world.csproj /src

RUN dotnet restore hello-world.csproj

COPY . /src

RUN dotnet publish "hello-world.csproj" -c Release -o /built

###################
## Runtime stage ##
###################

FROM mcr.microsoft.com/dotnet/runtime:10.0-alpine AS runtime

LABEL org.opencontainers.image.source=https://github.com/notalib/workshop-containerisation

RUN apk upgrade --no-cache --update && \
    apk add --no-cache --update tzdata && \
    cp -r /usr/share/zoneinfo/Europe/Copenhagen /etc/localtime && \
    echo "Europe/Copenhagen" >  /etc/timezone

# Only for the debian-based runtime
# RUN apt update && \
#     apt upgrade -y && \
#     apt install -y locales locales-all && \
#     rm -rf /var/lib/apt/lists/*

# To avoid .NET Culture issues, define Danish locale & timezone.
ENV TZ=Europe/Copenhagen
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
