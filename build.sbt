name := "scraper"

assemblyJarName := "scraper.jar"

scalaVersion := "2.13.3"

test in assembly := {}

libraryDependencies ++= Seq(
  "org.scalatest"     %% "scalatest"          % "3.2.+" % Test,
  "com.typesafe"      % "config"              % "1.4.+",
  // Akka
  "com.typesafe.akka" %% "akka-actor"         % "2.6.+",
  // Web scraper
  "org.jsoup"         %  "jsoup"              % "1.13.+",
  // DB
  "org.scalikejdbc"   %% "scalikejdbc"        % "3.5.+",
  "org.xerial"        %  "sqlite-jdbc"        % "3.32.+",
  "ch.qos.logback"    %  "logback-classic"    % "1.2.+",
  // XML
  "org.scala-lang.modules" %% "scala-xml"     % "1.3.+",
  "org.apache.commons"     % "commons-text"   % "1.9"
)

fork := true

scalacOptions ++= Seq(
  "-deprecation"
)

