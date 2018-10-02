package pages

import org.openqa.selenium.{By, WebElement}
import org.scalatest.selenium.WebBrowser._
import testkit.WebPageUtils._

trait CommonElements extends BrowserPage {

  def alertText = cssSelector(".alert-success.alert-dismissible").element

  def continueButtonQuery = cssSelector("[value='Continue']")
}
