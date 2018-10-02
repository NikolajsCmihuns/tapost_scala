package pages

import org.openqa.selenium.WebDriver

class TopMenuPage(implicit val webDriver: WebDriver) extends BrowserPage {

  override def pageIsReady: Any = true

  def gotToRegisterPage() = ()

}
