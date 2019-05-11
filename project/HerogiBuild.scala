import sbt._
import Keys._
import com.typesafe.sbt.SbtPgp.autoImportImpl._

object HerogiBuild extends Build {

  lazy val commonSettings = Defaults.coreDefaultSettings ++ Seq(
    organization := "com.herogi.client",
    version := "1.1.0-SNAPSHOT",
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
      "org.mockito"                 %   "mockito-core"       % "2.2.9"   % "test"),


    useGpg := true,
    pomIncludeRepository := { _ => false },

    licenses := Seq("MIT" -> url("https://opensource.org/licenses/MIT")),

    homepage := Some(url("https://herogi.com")),

    scmInfo := Some(
      ScmInfo(
        url("https://github.com/Herogi/herogi-scala"),
        "git@github.com:Herogi/herogi-scala.git"
      )
    ),

    pomExtra := extraPom,

    publishMavenStyle := true,

    publishTo := {
      val nexus = "https://oss.sonatype.org/"
      if (isSnapshot.value)
        Some("snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("releases"  at nexus + "service/local/staging/deploy/maven2")
    },

    publishArtifact in Test := true
  )

  lazy val root = Project(
    id = "herogi-client",
    base = file("."),
    settings = commonSettings)

  def extraPom = <developers>
    <developer>
      <email>info@herogi.com</email>
      <name>Herogi Team</name>
      <url>https://github.com/Herogi</url>
      <id>funkyeng</id>
    </developer>
  </developers>
}