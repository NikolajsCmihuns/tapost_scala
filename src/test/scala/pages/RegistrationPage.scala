package pages

import org.openqa.selenium.WebDriver

class RegistrationPage(implicit val webDriver: WebDriver) extends BrowserPage {
  override def pageIsReady: Any = true
}
