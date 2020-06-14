name := "scraper"

scalaVersion := "2.13.2"

libraryDependencies ++= Seq(
  "org.scalatest"     %% "scalatest"          % "3.1.+" % Test,
  "com.typesafe"      % "config"              % "1.4.+",
  // Akka
  "com.typesafe.akka" %% "akka-actor"         % "2.6.+",
  // Web scraper
  "org.jsoup"         %  "jsoup"              % "1.13.+",
  // DB
  "org.scalikejdbc"   %% "scalikejdbc"        % "3.+",
  "org.xerial"        %  "sqlite-jdbc"        % "3.+",
  "ch.qos.logback"    %  "logback-classic"    % "1.2.+",
  // XML
  "org.scala-lang.modules" %% "scala-xml"     % "1.3.+",
  "org.apache.commons"     % "commons-text"   % "1.8"

)

fork := true

scalacOptions ++= Seq(
  "-deprecation"
)

