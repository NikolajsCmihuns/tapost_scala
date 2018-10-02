package pages

import org.openqa.selenium.{By, WebDriver}
import org.scalatest.selenium.WebBrowser._
import testkit.WebPageUtils._

class MyAccountPage(implicit protected val webDriver: WebDriver) extends BrowserPage {

  override def pageIsReady: Boolean = pageTitle == "My Account"

  def goToMyAccountInformationPage(): MyAccountInformationPage = {
    val leftMenu = waitUntilDisplayedAndReturn(id("content"), "")
    underlyingWebElement(leftMenu, By.linkText("Edit Account")) map safeClick
    new MyAccountInformationPage
  }
}
