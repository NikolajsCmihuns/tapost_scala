package testkit

import com.typesafe.scalalogging.LazyLogging
import org.openqa.selenium.WebDriver
import org.scalatest.selenium.WebBrowser._

import scala.util.Try

object ScreenShotUtil extends LazyLogging {

  def makeScreenShot(suiteName: String, testName: String, message: String = "")(implicit webDriver: WebDriver): Try[Unit] = {
    setCaptureDir("target/test-reports")
    Try(capture to s"""$suiteName-${testName.replace(" ", "_").replace("/", "_")}_$message.png""")
      .recover {
        case e: Throwable => logger.error(s"Failed to take screenshot for $suiteName.$testName: $e")
      }
  }
}