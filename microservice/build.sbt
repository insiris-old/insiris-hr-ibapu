name := """play-java"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  filters,
  javaJdbc,
  cache,
  javaWs,
  "com.datastax.cassandra" % "cassandra-driver-core" % "3.0.2",
  "com.google.code.gson" % "gson" % "2.7"

)


fork in run := true