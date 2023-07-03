ThisBuild / scalaVersion := "2.13.5"

lazy val root = (project in file("."))
  .settings(
    name := "Ipify",
    version := "0.1",
    scalaVersion := "2.13.5",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "1.0.12",
      "dev.zio" %% "zio-test" % "1.0.12" % "test",
      "dev.zio" %% "zio-test-sbt" % "1.0.12" % "test"
    )
  )
