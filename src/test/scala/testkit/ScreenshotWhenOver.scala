package testkit

import org.openqa.selenium.WebDriver
import org.scalatest.{Outcome, TestSuite, TestSuiteMixin}
import testkit.ScreenShotUtil.makeScreenShot

trait ScreenshotWhenOver extends TestSuiteMixin {

  this: TestSuite =>

  implicit def webDriver: WebDriver

  abstract override def withFixture(test: NoArgTest): Outcome = {
    val outcome = super.withFixture(test)
    if (!outcome.isSucceeded) makeScreenShot(suiteName, test.name)(webDriver)
    outcome
  }
}