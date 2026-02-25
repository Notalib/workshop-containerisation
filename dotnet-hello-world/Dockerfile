# See https://learn.microsoft.com/en-us/dotnet/core/docker/build-container?tabs=windows&pivots=dotnet-10-0

#################
## Build stage ##
#################

FROM mcr.microsoft.com/dotnet/sdk:10.0 AS builder

WORKDIR /src

###################
## Runtime stage ##
###################

FROM mcr.microsoft.com/dotnet/runtime:10.0 AS runtime

LABEL org.opencontainers.image.source=https://github.com/notalib/workshop-containerisation

WORKDIR /app

ENTRYPOINT [ "dotnet", "hello-world.dll" ]
