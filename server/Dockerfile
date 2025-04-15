# --- STEP 1: Build & create custom JRE ---
FROM eclipse-temurin:17-jdk-jammy AS builder

WORKDIR /workspace
COPY build/libs/aniflix.jar .

# Extract and create slim JRE with only needed modules
RUN mkdir unpacked && cd unpacked && jar -xf ../aniflix.jar && \
    jlink --no-header-files --no-man-pages --compress=2 \
    --add-modules java.base,java.logging,java.management,java.naming,java.sql \
    --output /workspace/slimjre

# --- STEP 2: Minimal runtime with distroless ---
FROM gcr.io/distroless/base-debian11

# Setup env
ENV TZ=Europe/Amsterdam \
    SPRING_PROFILES_INCLUDE=development \
    JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport" \
    HEAP_SIZE_MIN=512m \
    HEAP_SIZE=4096m

WORKDIR /opt/app

# Copy runtime from builder
COPY --from=builder /workspace/slimjre ./slimjre
COPY --from=builder /workspace/unpacked/BOOT-INF/lib ./lib
COPY --from=builder /workspace/unpacked/META-INF ./META-INF
COPY --from=builder /workspace/unpacked/BOOT-INF/classes ./
COPY config ./config

# Expose port
EXPOSE 8080

# Healthcheck is skipped due to distroless; use Kubernetes liveness/readiness probes instead

# Run application
ENTRYPOINT ["/opt/app/slimjre/bin/java"]
CMD [
  "-Xms512m",
  "-Xmx4096m",
  "-Djava.security.egd=file:/dev/urandom",
  "-Djava.awt.headless=true",
  "-cp",
  "/opt/app:/opt/app/lib/*",
  "org.jordijaspers.aniflix.Aniflix",
  "--spring.config.additional-location=/opt/app/config/",
  "--spring.output.ansi.enabled=always",
  "--spring.profiles.include=development"
]
