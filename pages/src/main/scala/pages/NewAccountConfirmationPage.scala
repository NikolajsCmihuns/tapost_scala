package pages

import org.openqa.selenium.WebDriver
import testkit.WebPageUtils._
import org.scalatest.selenium.WebBrowser._

class NewAccountConfirmationPage(implicit protected val webDriver: WebDriver) extends BrowserPage {

  override def pageIsReady: Boolean = {
    cssSelector("[id='content']:first-child p").findAllElements
      .toSeq.exists(e => elementText(e).startsWith("Congratulations! Your new account has been successfully created!"))
  }

}
