package pages

import org.openqa.selenium.WebDriver
import org.scalatest.MustMatchers
import org.scalatest.concurrent.Eventually
import testkit.WebPageUtils._

trait BrowserPage extends Eventually with MustMatchers with InternalPagePatienceConfig {

  waitUntil(
    pageIsReady,
    "Page was not ready"
  )

  def pageIsReady: Boolean

  implicit protected val webDriver: WebDriver

}
