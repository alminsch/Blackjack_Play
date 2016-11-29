name := """Blackjack"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.webjars" % "bootstrap" % "3.3.7",
  javaJdbc,
  cache,
  javaWs,
  "log4j" % "log4j" % "1.2.17"
)
// Compile the project before generating Eclipse files, so that generated .scala or .class files for views and routes are present
EclipseKeys.preTasks := Seq(compile in Compile)

fork in run := true

fork in run := true