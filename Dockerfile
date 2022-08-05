FROM ubuntu:jammy

ENV SCALA_VERSION=2.13.8 \
    SCALA_HOME=/usr/share/scala \
    SBT_VERSION=1.6.2 \
    PATH="/usr/local/sbt/bin/:$PATH"

RUN apt-get update && apt-get install -y \
    curl \
    gzip \
    wget \
    openjdk-18-jdk && \
    rm -rf /var/lib/apt/lists/*

# Download SBT
RUN curl -fL https://github.com/coursier/launchers/raw/master/cs-x86_64-pc-linux.gz | gzip -d >/usr/bin/coursier && \
    chmod +x /usr/bin/coursier && \
    cd "/tmp" && \
    coursier bootstrap scala:${SCALA_VERSION} --standalone -o /usr/bin/scala && \
    coursier bootstrap sbt:${SBT_VERSION} --standalone -o /usr/bin/sbt

# wget --no-verbose "https://downloads.typesafe.com/scala/${SCALA_VERSION}/scala-${SCALA_VERSION}.tgz" && \
# tar xzf "scala-${SCALA_VERSION}.tgz" && \
# mkdir "${SCALA_HOME}" && \
# rm "/tmp/scala-${SCALA_VERSION}/bin/"*.bat && \
# mv "/tmp/scala-${SCALA_VERSION}/bin" "/tmp/scala-${SCALA_VERSION}/lib" "${SCALA_HOME}" && \
# ln -s "${SCALA_HOME}/bin/"* "/usr/bin/" && \
# apk del build-deps && \
# rm -rf "/tmp/"* && \
# mkdir -p "/usr/local/" && \
# wget "https://github.com/sbt/sbt/releases/download/v${SBT_VERSION}/sbt-${SBT_VERSION}.tgz" && \
# tar xzf "sbt-${SBT_VERSION}.tgz" -C "/usr/local/" && \
# sbt -version

# Create app
WORKDIR /app
COPY . .
RUN sbt assembly && \
    chmod +x /app/target/scala-2.13/scraper.jar && \
    rm -rf ~/.ivy2/
