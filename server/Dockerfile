# ---- Stage 1: Build & Slim JRE ----
FROM eclipse-temurin:17-jdk-jammy AS build

LABEL maintainer="Jordi Jaspers" description="Aniflix Server Docker Image"

WORKDIR /workspace

# Copy only the jar, not full source
ADD build/libs/aniflix.jar .

# Generate custom JRE with only required modules
RUN jlink \
  --no-header-files \
  --no-man-pages \
  --compress=2 \
  --strip-java-debug-attributes \
  --add-modules java.base,java.logging,java.xml,java.sql,java.naming,jdk.unsupported \
  --output slimjre

# Unpack Spring Boot fat jar
RUN mkdir unpacked && cd unpacked && jar -xf /workspace/aniflix.jar

# ---- Stage 2: Runtime Image ----
FROM ubuntu:jammy

# Install only required dependencies, remove unused tools
RUN apt-get update && \
  apt-get install -y --no-install-recommends \
    tzdata fontconfig ca-certificates curl wget libtcnative-1 && \
  rm -rf /var/lib/apt/lists/*

ENV LANG="en_US.UTF-8" TZ="Europe/Amsterdam"

# Create non-root user and dirs
RUN groupadd -g 10001 aniflix && \
    useradd -m -u 10000 -g 10001 aniflix && \
    mkdir -p /opt/workspace/app /opt/workspace/config && \
    chown -R aniflix:aniflix /opt/workspace

USER aniflix
WORKDIR /opt/workspace

# Copy slim JRE
COPY --from=build --chown=aniflix:aniflix /workspace/slimjre slimjre/

# Copy config and unpacked Spring Boot app
COPY --chown=aniflix:aniflix config config/
COPY --from=build --chown=aniflix:aniflix /workspace/unpacked/BOOT-INF/lib app/lib
COPY --from=build --chown=aniflix:aniflix /workspace/unpacked/META-INF app/META-INF
COPY --from=build --chown=aniflix:aniflix /workspace/unpacked/BOOT-INF/classes app

# Healthcheck
HEALTHCHECK --interval=30s --timeout=5s --retries=3 CMD curl -f http://localhost:8080/api/actuator/health || exit 1

# Expose ports
EXPOSE 8080 8009

# Runtime environment variables
ENV SPRING_PROFILES_INCLUDE="development" \
    JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport" \
    HEAP_SIZE_MIN="512m" \
    HEAP_SIZE="1024m" \
    METASPACE_SIZE_MIN="128m" \
    METASPACE_SIZE="256m" \
    METASPACE_FREE_RATIO="60"

# Start Spring Boot app
CMD slimjre/bin/java \
  -Xms${HEAP_SIZE_MIN} -Xmx${HEAP_SIZE} \
  -XX:MetaspaceSize=${METASPACE_SIZE_MIN} -XX:MaxMetaspaceSize=${METASPACE_SIZE} \
  -XX:MaxMetaspaceFreeRatio=${METASPACE_FREE_RATIO} \
  -XX:+UseG1GC \
  -Djava.security.egd=file:/dev/urandom \
  -Djava.awt.headless=true \
  -Dspring.devtools.restart.enabled=false \
  -Djava.library.path=slimjre/lib \
  -cp app:app/lib/* org.jordijaspers.aniflix.Aniflix \
  --spring.config.additional-location=/opt/workspace/config/ \
  --spring.output.ansi.enabled=always \
  --spring.profiles.include=${SPRING_PROFILES_INCLUDE}
