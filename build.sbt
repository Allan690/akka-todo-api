enablePlugins(JavaAppPackaging, AshScriptPlugin)

name := "akkahttp-quickstart"

version := "0.1"

scalaVersion := "2.13.1"

dockerBaseImage := "openjdk:8-jre-alpine"
packageName in Docker := "akkahttp-quickstart"

val akkaVersion = "2.6.3"
val akkaHttpVersion = "10.1.11"
val circeVersion = "0.12.3"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "de.heikoseeberger" %% "akka-http-circe" % "1.30.0",
  "org.scalatest" %% "scalatest" % "3.1.0" % Test,
)
