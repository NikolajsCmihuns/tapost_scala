package pages

import org.openqa.selenium.WebDriver
import org.scalatest.selenium.WebBrowser._

class MyAccountPage(implicit protected val webDriver: WebDriver) extends BrowserPage {

  override def pageIsReady: Boolean = pageTitle == "My Account"

}
