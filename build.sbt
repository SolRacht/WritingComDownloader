name := "scraper"

scalaVersion := "2.13.0"

libraryDependencies ++= Seq(
  "org.scalatest"     %% "scalatest"          % "3.0.+" % Test,
  "com.typesafe"      % "config"              % "1.3.+",
  // Akka
  "com.typesafe.akka" %% "akka-actor"         % "2.5.+",
  // Web scraper
  "org.jsoup"         %  "jsoup"              % "1.12.+",
  // DB
  "org.scalikejdbc"   %% "scalikejdbc"        % "3.+",
  "org.xerial"        %  "sqlite-jdbc"        % "3.+",
  "com.h2database"    %  "h2"                 % "1.4.+",
  "ch.qos.logback"    %  "logback-classic"    % "1.2.+",
  // XML
  "org.scala-lang.modules" %% "scala-xml"     % "1.2.0",
  "org.apache.commons"     % "commons-text"   % "1.6"

)


fork := true
