ThisBuild / name := "tapost_scala"
ThisBuild / version := "0.1"
ThisBuild / scalaVersion := "2.12.7"
ThisBuild / concurrentRestrictions in Global := Seq(Tags.limit(Tags.Test, 10))

lazy val root = (project in file("."))
  .aggregate(specs)
  .dependsOn(specs)

lazy val browser = (project in file("browser"))
  .settings(
    libraryDependencies ++= Seq(
      "org.seleniumhq.selenium" % "selenium-java" % "3.14.0",
	  "org.seleniumhq.selenium" % "selenium-chrome-driver" % "3.14.0",
	  "org.seleniumhq.selenium" % "selenium-firefox-driver" % "3.14.0",
	  "io.github.bonigarcia" % "webdrivermanager" % "3.0.0"
    )
  )

lazy val testkit = (project in file("testkit"))
  .aggregate(browser)
  .dependsOn(browser)
  .settings(
    libraryDependencies ++= Seq(
      "org.pegdown" % "pegdown" % "1.6.0",
	  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2",
	  "org.scalatest" %% "scalatest" % "3.0.5",
  	  "com.github.t3hnar" %% "scalax" % "3.4",
	  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0"
	)
  )

lazy val pages = (project in file("pages"))
  .aggregate(testkit)
  .dependsOn(testkit)
  .settings(
    libraryDependencies ++= Seq(
	  "org.scalatest" %% "scalatest" % "3.0.5"
	)
  )
  
lazy val specs = (project in file("specs"))
  .aggregate(pages)
  .dependsOn(pages)
  .settings(
    testOptions in Test := Seq(
	  Tests.Argument(TestFrameworks.ScalaTest, "-h"),
	  Tests.Argument(TestFrameworks.ScalaTest, "target/test-reports-html"),
	  Tests.Argument(TestFrameworks.ScalaTest, "-u"),
	  Tests.Argument(TestFrameworks.ScalaTest, "target/test-reports"),
	  Tests.Argument(TestFrameworks.ScalaTest, "-oDF")
	)
  )