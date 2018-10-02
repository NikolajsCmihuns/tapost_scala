package testkit

import java.io.FileWriter

import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat
import org.openqa.selenium.WebDriver
import org.scalatest.{Outcome, TestSuite, TestSuiteMixin}

import scala.collection.JavaConverters._
import scala.util.Try

trait BrowserLogWhenOver extends TestSuiteMixin {
  this: TestSuite =>

  implicit def webDriver: WebDriver

  abstract override def withFixture(test: NoArgTest): Outcome = {

    val outcome = super.withFixture(test)

    Try {
      val logs = webDriver.manage().logs().get("browser").getAll.asScala.toList

      val logFilename = s"""$suiteName-${test.name.replace(" ", "_").replace("/", "_")}.log"""
      val writer = new FileWriter(s"target/test-reports/$logFilename")
      val formatter = ISODateTimeFormat.basicDateTime()
      logs.foreach {
        entry =>
          val time = new DateTime(entry.getTimestamp).toString(formatter)
          val level = entry.getLevel.getName
          val message = entry.getMessage
          writer.write(s"$time [$level] $message\n")
      }
      writer.close()
    }

    outcome
  }
}
