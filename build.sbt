name := "tapost_scala"

version := "0.1"

scalaVersion := "2.12.6"

concurrentRestrictions in Global := Seq(Tags.limit(Tags.Test, 10))

javaOptions in Test ++= sys.props.toSeq map { case (key, value) => s"-D$key=$value" }

testOptions in Test := Seq(
  Tests.Argument(TestFrameworks.ScalaTest, "-h"),
  Tests.Argument(TestFrameworks.ScalaTest, "target/test-reports-html"),
  Tests.Argument(TestFrameworks.ScalaTest, "-u"),
  Tests.Argument(TestFrameworks.ScalaTest, "target/test-reports"),
  Tests.Argument(TestFrameworks.ScalaTest, "-oDF")
)

logBuffered := true // this is needed to make sure the logs of parallel tests are not mixed up

libraryDependencies ++= Seq(
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test,
  "org.scalatest" %% "scalatest" % "3.0.5" % Test,
  "org.seleniumhq.selenium" % "selenium-java" % "3.11.0" % Test,
  "org.seleniumhq.selenium" % "selenium-chrome-driver" % "3.11.0" % Test,
  "org.seleniumhq.selenium" % "selenium-firefox-driver" % "3.11.0" % Test,
  "io.github.bonigarcia" % "webdrivermanager" % "2.2.0" % Test,
  "com.typesafe.scala-logging" %% "scala-logging" % "3.8.0" % Test,
  "org.pegdown" % "pegdown" % "1.6.0" % Test,
  "com.github.t3hnar" %% "scalax" % "3.3"
) map excludeLog4j

def excludeLog4j(moduleID: ModuleID): ModuleID = moduleID excludeAll(
  ExclusionRule("log4j", "log4j"),
  ExclusionRule("org.slf4j", "slf4j-log4j12"),
  ExclusionRule("commons-logging", "commons-logging")
)