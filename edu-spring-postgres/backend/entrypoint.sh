#!/bin/sh
set -e

if [ -f /run/secrets/db-password ]; then
  export POSTGRES_PASSWORD="$(cat /run/secrets/db-password)"
fi

# Run Spring Boot app
# Start the application jar - this is not the uber jar used by the builder
# This jar only contains application code and references to the extracted jar files
# This layout is efficient to start up and AOT cache (and CDS) friendly
# -XX:+UseContainerSupport is on by default in Java 11+ but explicit is better than implicit.
# -XX:MaxRAMPercentage=75.0 tells the JVM to use up to 75% of the container’s available memory for the heap. Leaving the remaining 25% for native memory, Metaspace, and thread stacks is a reasonable default for most applications.
# -XX:+ExitOnOutOfMemoryError makes the JVM exit (and the container restart) rather than limping along in a degraded state when it runs out of memory

exec java -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:+ExitOnOutOfMemoryError -jar project.jar
