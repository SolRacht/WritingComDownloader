FROM hseeberger/scala-sbt:graalvm-ce-20.0.0-java11_1.4.2_2.13.3
COPY . .
RUN sbt assembly
RUN chmod +x /root/target/scala-2.13/scraper.jar