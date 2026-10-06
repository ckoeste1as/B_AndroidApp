#!/bin/sh
set -eu
APP_HOME=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
WRAPPER_JAR="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"
if [ ! -f "$WRAPPER_JAR" ]; then
    echo 'Downloading official Gradle 8.13 wrapper ...'
    DOWNLOAD="$WRAPPER_JAR.download"
    trap 'rm -f "$DOWNLOAD"' EXIT HUP INT TERM
    curl --fail --location --silent --show-error 'https://raw.githubusercontent.com/gradle/gradle/v8.13.0/gradle/wrapper/gradle-wrapper.jar' -o "$DOWNLOAD"
    EXPECTED=$(curl --fail --location --silent --show-error 'https://services.gradle.org/distributions/gradle-8.13-wrapper.jar.sha256')
    if command -v sha256sum >/dev/null 2>&1; then
        ACTUAL=$(sha256sum "$DOWNLOAD" | cut -d ' ' -f 1)
    else
        ACTUAL=$(shasum -a 256 "$DOWNLOAD" | cut -d ' ' -f 1)
    fi
    if [ "$ACTUAL" != "$EXPECTED" ]; then
        echo 'Gradle wrapper checksum mismatch.' >&2
        exit 1
    fi
    mv "$DOWNLOAD" "$WRAPPER_JAR"
    trap - EXIT HUP INT TERM
fi
if [ -n "${JAVA_HOME:-}" ]; then
    JAVA_EXE="$JAVA_HOME/bin/java"
else
    JAVA_EXE=java
fi
exec "$JAVA_EXE" ${JAVA_OPTS:-} ${GRADLE_OPTS:-} "-Dorg.gradle.appname=gradlew" -classpath "$WRAPPER_JAR" org.gradle.wrapper.GradleWrapperMain "$@"
