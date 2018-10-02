package pages

import org.openqa.selenium.WebDriver
import org.scalatest.selenium.WebBrowser._
import testkit.StringUtils._
import testkit.WebPageUtils._

class RegistrationFormPage(implicit protected val webDriver: WebDriver) extends BrowserPage {

  private def firstNameQuery = id("input-firstname")
  private def lastNameQuery = id("input-lastname")
  private def emailQuery = id("input-email")
  private def telephoneNumberQuery = id("input-telephone")
  private def passwordQuery = id("input-password")
  private def confirmPasswordQuery = id("input-confirm")
  private def subscribeToNewsletterQuery = name("")
  private def subscribeNoQuery = cssSelector("input[value='1']")
  private def agreeCheckQuery = name("agree")
  private def continueButtonQuery = cssSelector("[value='Continue']")

  override def pageIsReady: Boolean = elementDisplayed(firstNameQuery)

  def register(user: User): NewAccountConfirmationPage = {
    firtsNameField.value_=(user.firstName)
    lastNameField.value_=(user.lastName)
    emailInputField.value_=(user.email)
    telephoneField.value_=(user.telephone)
    passwordField.value_=(user.password)
    confirmPasswordField.value_=(user.password)
    agreeCheckBox.select()
    safeClick(continueButtonQuery)
    new NewAccountConfirmationPage
  }

  def firtsNameField = textField(firstNameQuery)
  def lastNameField = textField(lastNameQuery)
  def emailInputField = emailField(emailQuery)
  def telephoneField = telField(telephoneNumberQuery)
  def passwordField = pwdField(passwordQuery)
  def confirmPasswordField = pwdField(confirmPasswordQuery)
  def subscribeNoRadio = radioButton(subscribeNoQuery)
  def agreeCheckBox = checkbox(agreeCheckQuery)



}

/**
  *
  * @param firstName  First Name must be between 1 and 32 characters!
  * @param lastName Last Name must be between 1 and 32 characters!
  * @param email E-Mail Address does not appear to be valid!
  * @param telephone Telephone must be between 3 and 32 characters!
  * @param password Password must be between 4 and 20 characters!
  * @param subscribeToNewsLetter
  */

case class User(
  firstName: String,
  lastName: String,
  email: String,
  telephone: String,
  password: String,
  subscribeToNewsLetter: Boolean
)
object User {
  def newUser(subscribeToNewsLetter: Boolean = false): User = {
    User(
      firstName = uniqueString(10),
      lastName= uniqueString(10),
      email = s"${uniqueString(5)}@abc.com",
      telephone =  "+371 22223333",
      password = uniqueString(10),
      subscribeToNewsLetter = subscribeToNewsLetter
    )
  }
}