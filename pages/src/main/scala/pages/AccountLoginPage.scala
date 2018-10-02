package pages

import org.openqa.selenium.WebDriver
import org.scalatest.selenium.WebBrowser._
import testkit.WebPageUtils._

class AccountLoginPage(implicit protected val webDriver: WebDriver) extends BrowserPage {
  override def pageIsReady: Boolean = {
    pageTitle == "Account Login" && elementDisplayed(emailAddressField)
  }

  def login(email: String, password: String) = {
    emailAddressField.value_=(email)
    passwordField.value_=(password)
    safeClick(loginButton)
    new MyAccountPage
  }

  private def emailAddressField = emailField("email")
  private def passwordField = pwdField("password")
  private def loginButton = cssSelector("input[value='Login']")
}
