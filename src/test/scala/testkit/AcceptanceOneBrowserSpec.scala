package testkit

import browser.{ChromeExecutable, CustomizedChromeDriver}
import org.openqa.selenium.WebDriver
import org.scalatest._
import org.scalatest.concurrent.Eventually
import org.scalatestplus.play.{ChromeFactory, OneBrowserPerSuite}

import scala.util.Try

trait AcceptanceOneBrowserSpec extends WordSpec
  with Eventually
  with SpecPatienceConfig
  with MustMatchers
  with OptionValues
  with GivenWhenThen
  with BeforeAndAfterAll
  with ChromeFactory
  with ChromeExecutable
  with OneBrowserPerSuite
  with ScalaTestPlusPlay
  with ScreenshotWhenOver
  with BrowserLogWhenOver {

  override def createWebDriver(): WebDriver = {
    CustomizedChromeDriver.createWebDriver()
  }

  override def afterAll = {
    Try(webDriver.quit())
    super.afterAll()
  }
}