scalaVersion := "2.13.3"
version := "0.1.0-SNAPSHOT"
organization := "com.bponder"
organizationName := "ponder"
name := "HEB-Rest-Test"

libraryDependencies ++= Seq(
  "dev.zio"        %% "zio"      % "2.0.4",
  "dev.zio"        %% "zio-json" % "0.3.0-RC11",
  "dev.zio"        %% "zio-http" % "0.0.3",
  "com.h2database" % "h2"        % "2.1.214"
)

lazy val doobieVersion = "1.0.0-RC1"

libraryDependencies ++= Seq(
  "io.github.gaelrenoux" %% "tranzactio"      % "4.0.0",
  "org.tpolecat"         %% "doobie-core"     % doobieVersion,
  "org.tpolecat"         %% "doobie-postgres" % doobieVersion,
  "org.tpolecat"         %% "doobie-specs2"   % doobieVersion
)

val circeVersion = "0.14.1"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)
