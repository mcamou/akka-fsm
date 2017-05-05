organization := "com.typesafe.akka.samples"
name := "akka-sample-fsm-scala"

val akkaVersion = "2.4.17"

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)

ensimeScalaJars := Seq.empty
ensimeServerJars := Seq.empty

licenses := Seq(("CC0", url("http://creativecommons.org/publicdomain/zero/1.0")))
