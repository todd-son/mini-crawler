name := "mini-crawler"
version := "0.1"
scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  "com.softwaremill.sttp.client" %% "core" % "2.0.0-RC5",
  "com.softwaremill.sttp.client" %% "circe" % "2.0.0-RC5",
  "org.apache.logging.log4j" % "log4j-core" % "2.12.1",
  "org.apache.logging.log4j" % "log4j-slf4j-impl" % "2.12.1",
  "org.json" % "json" % "20190722",
  "io.circe" %% "circe-core" % "0.12.3",
  "io.circe" %% "circe-generic" % "0.12.3",
  "io.circe" %% "circe-parser" % "0.12.3",
  "org.mongodb.scala" %% "mongo-scala-driver" % "4.4.2",
  "net.ruippeixotog" %% "scala-scraper" % "3.0.0",
  "org.scala-lang.modules" %% "scala-xml" % "1.2.0",
  "org.mockito" % "mockito-core" % "3.2.0" % "it,test",
  "org.scalatest" %% "scalatest" % "3.0.8" % "it,test",
  "org.seleniumhq.selenium" % "selenium-java" % "4.2.2"
)

lazy val root = (project in file("."))
  .configs(IntegrationTest)
  .settings(
    Defaults.itSettings,
    libraryDependencies ++= Seq()
  )
