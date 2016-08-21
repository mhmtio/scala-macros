name := "scala-macros"

version := "1.0"

scalaVersion := "2.11.7"


scalacOptions ++= Seq("-deprecation", "-feature")
libraryDependencies ++= Seq(
    "org.scala-lang" % "scala-reflect" % scalaVersion.value,
    "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.1",
    "org.specs2" %% "specs2" % "2.3.12" % "test"
  )

