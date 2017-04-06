import sbt._
import Keys._

object HerogiBuild extends Build {

  lazy val commonSettings = Defaults.coreDefaultSettings ++ Seq(
    organization := "com.herogi.client",
    version := "1.0.0",
    scalaVersion := "2.11.9",

    parallelExecution in Test := false,

    //
    //  Common resolvers
    //
    resolvers ++= Seq(
      "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
      "spray repo" at "http://repo.spray.io",
      "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/"
    ),

    //
    //  Common dependencies for each sub project, put project specific dependencies should move
    //  into build.sbt for each project
    //
    libraryDependencies ++= Seq(
      "com.typesafe.akka"           %% "akka-http"           % "10.0.5",
      "com.typesafe.scala-logging"  %% "scala-logging"       % "3.5.0",
      "io.spray"                    % "spray-json_2.11"      % "1.3.3",
      "com.typesafe.akka"           % "akka-http-spray-json_2.11" % "10.0.5",
      "org.scalatest"               %% "scalatest"           % "3.0.1"   % "test",
      "org.mockito"                 %   "mockito-core"       % "2.2.9"   % "test")

  )

  lazy val root = Project(
    id = "herogi-client",
    base = file("."),
    settings = commonSettings)
}