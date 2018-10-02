package pages

import org.openqa.selenium.WebDriver
import org.scalatest.selenium.WebBrowser._
import testkit.WebPageUtils._

class TopMenuPage(implicit val webDriver: WebDriver) extends BrowserPage {

  private val MyAccount = linkText("My Account")
  private val Register = linkText("Register")

  override def pageIsReady: Any = true

  def goToRegisterPage() = {
    safeClick(MyAccount)
    safeClick(Register)
    new RegistrationFormPage
  }

}
