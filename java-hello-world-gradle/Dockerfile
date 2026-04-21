###############################
### ---- Build stage ---- ###
###############################
FROM eclipse-temurin:21-jdk AS build

ARG GRADLE_VERSION=9.4.1

ENV GRADLE_HOME=/opt/gradle \
    GRADLE_USER_HOME=/cache/.gradle \
    GRADLE_OPTS="-Dorg.gradle.daemon=false \
    -Dorg.gradle.parallel=true \
    -Dorg.gradle.caching=true \
    -Xmx2g"

# ⭐ Setup desired gradle version
RUN apt-get update && apt-get install -y --no-install-recommends unzip \
    && wget -q https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip \
    && unzip gradle-${GRADLE_VERSION}-bin.zip -d /opt \
    && ln -s /opt/gradle-${GRADLE_VERSION} /opt/gradle \
    && rm gradle-${GRADLE_VERSION}-bin.zip \
    && apt-get remove -y unzip \
    && rm -rf /var/lib/apt/lists/*

ENV PATH="${GRADLE_HOME}/bin:${PATH}"

WORKDIR /src

RUN mkdir -p ./app

# ⭐ Copy in gradle settings and build files
COPY settings.gradle.kts ./ \
     app/build.gradle.kts ./

# ⭐ Let gradle resolve all dependencies first
RUN --mount=type=cache,target=/cache/.gradle \
    gradle dependencies --no-daemon --stacktrace

COPY app/ app/

# ⭐ Build with gradle from src
RUN --mount=type=cache,target=/cache/.gradle \
    gradle build -x test --no-daemon --stacktrace --build-cache

###############################
### ---- Runtime stage ---- ###
###############################
FROM eclipse-temurin:21-jre AS runtime

RUN groupadd -g 1001 app-group && \
    useradd -u 1001 -g app-group -m -d /app -s /bin/false app-user

WORKDIR /app

# ⭐ Copy built far jar into the runtime image
COPY --from=build --chown=app-user:app-group /src/app/build/libs/app-standalone.jar app.jar

# ⭐ Set containerization-relevant Java runtime options
ENV JAVA_OPTS="-server \
    -XX:+UseContainerSupport \
    -XX:MaxRAMPercentage=75.0 \
    -XX:+UseG1GC \
    -Djava.security.egd=file:/dev/./urandom"

# ⭐ Run as non-root user
USER app-user

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
