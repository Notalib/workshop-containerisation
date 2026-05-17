#################
## Build stage ##
#################

FROM --platform=$BUILDPLATFORM golang:1.26 AS builder
ARG TARGETOS
ARG TARGETARCH

WORKDIR /src

# Fetch go requirements
COPY go.mod go.sum* ./
RUN go mod download

# Copy sources
COPY *.go ./

# Build go native executable
RUN GOOS=${TARGETOS} GOARCH=${TARGETARCH} CGO_ENABLED=0 go build -o /output

###################
## Runtime stage ##
###################

FROM scratch AS runner

LABEL org.opencontainers.image.source=https://github.com/notalib/workshop-containerisation
LABEL org.opencontainers.image.description="Am extremely slim Go app example"

COPY --from=builder /output /go-cat

COPY static.txt /

ENTRYPOINT [ "/go-cat", "static.txt" ]
