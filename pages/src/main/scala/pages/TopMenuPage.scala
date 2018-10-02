package pages

import org.openqa.selenium.WebDriver
import org.scalatest.selenium.WebBrowser._
import testkit.WebPageUtils._

class TopMenuPage(implicit protected val webDriver: WebDriver) extends BrowserPage {

  private val MyAccount = linkText("My Account")
  private val Register = linkText("Register")
  private val Logout = linkText("Logout")
  private val Login = linkText("Login")

  override def pageIsReady: Boolean = true

  def goToRegisterPage(): RegistrationFormPage = {
    safeClick(MyAccount)
    safeClick(Register)
    new RegistrationFormPage
  }

  def goToLoginPage(): AccountLoginPage = {
    safeClick(MyAccount)
    safeClick(Login)
    new AccountLoginPage
  }

  def logout(): AccountLogoutPage = {
    safeClick(MyAccount)
    safeClick(Logout)
    new AccountLogoutPage
  }

}

object TopMenuPage {
  def prepare(implicit webDriver: WebDriver): TopMenuPage = {
    go to MainPage.url
    new TopMenuPage
  }
}
