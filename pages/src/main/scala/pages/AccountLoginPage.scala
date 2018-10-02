package pages

import org.openqa.selenium.WebDriver
import org.scalatest.selenium.WebBrowser
import org.scalatest.selenium.WebBrowser._
import testkit.WebPageUtils._

class AccountLoginPage(implicit protected val webDriver: WebDriver) extends BrowserPage {
  override def pageIsReady: Boolean = {
    pageTitle == "Account Login" && elementDisplayed(emailAddressField)
  }

  def login(email: String, password: String) = {
    emailAddressField.value_=(email)
    passwordField.value_=(password)
    clickOnLoginButton()
    new MyAccountPage
  }

  def clickOnLoginButton(): Unit = safeClick(loginButton)

  def alertMessage(): WebBrowser.Element = cssSelector(".alert-danger").element

  private def emailAddressField = emailField("email")
  private def passwordField = pwdField("password")
  private def loginButton = cssSelector("input[value='Login']")
}
