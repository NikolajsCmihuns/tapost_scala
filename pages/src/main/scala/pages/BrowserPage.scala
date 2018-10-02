package pages

import org.openqa.selenium.WebDriver
import org.scalatest.MustMatchers
import org.scalatest.concurrent.Eventually

trait BrowserPage extends Eventually with MustMatchers with InternalPagePatienceConfig {

  eventually(pageIsReady)

  def pageIsReady: Any

  implicit protected val webDriver: WebDriver

}
