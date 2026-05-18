FROM golang:1.26 AS build

WORKDIR /src

COPY sleep.go .

RUN CGO_ENABLED=0 go build -o /app/go-sleep sleep.go

#################
# Runtime Image #
#################
FROM gcr.io/distroless/base-debian13

COPY --from=build /app /app

ENTRYPOINT ["/app/go-sleep"]
