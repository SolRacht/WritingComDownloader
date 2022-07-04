FROM openjdk:18-jdk-alpine

ENV SCALA_VERSION=2.13.8 \
    SCALA_HOME=/usr/share/scala \
    SBT_VERSION=1.6.2 \
    PATH="/usr/local/sbt/bin/:$PATH"

# Download SBT
RUN apk add --no-cache --virtual=.build-dependencies wget ca-certificates && \
    apk add --no-cache bash && \
    cd "/tmp" && \
    wget --no-verbose "https://downloads.typesafe.com/scala/${SCALA_VERSION}/scala-${SCALA_VERSION}.tgz" && \
    tar xzf "scala-${SCALA_VERSION}.tgz" && \
    mkdir "${SCALA_HOME}" && \
    rm "/tmp/scala-${SCALA_VERSION}/bin/"*.bat && \
    mv "/tmp/scala-${SCALA_VERSION}/bin" "/tmp/scala-${SCALA_VERSION}/lib" "${SCALA_HOME}" && \
    ln -s "${SCALA_HOME}/bin/"* "/usr/bin/" && \
    apk del .build-dependencies && \
    rm -rf "/tmp/"* && \
    mkdir -p "/usr/local/" && \
    wget "https://github.com/sbt/sbt/releases/download/v${SBT_VERSION}/sbt-${SBT_VERSION}.tgz" && \
    tar xzf "sbt-${SBT_VERSION}.tgz" -C "/usr/local/" && \
    sbt -version

# Create app
WORKDIR /app
COPY . .
RUN sbt assembly && \
    chmod +x /app/target/scala-2.13/scraper.jar && \
    rm -rf ~/.ivy2/
