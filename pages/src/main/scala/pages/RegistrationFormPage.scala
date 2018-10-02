package pages

import org.openqa.selenium.WebDriver
import org.scalatest.selenium.WebBrowser._
import testkit.StringUtils._
import testkit.WebPageUtils._

class RegistrationFormPage(implicit protected val webDriver: WebDriver) extends BrowserPage {

  private def FirstName = id("input-firstname")
  private def LastName = id("input-lastname")
  private def Email = id("input-email")
  private def TelephoneNumber = id("input-telephone")
  private def Password = id("input-password")
  private def ConfirmPassword = id("input-confirm")
  private def SubscribeToNewsletter = name("")
  private def AgreeCheckBox = name("agree")
  private def ContinueButton = cssSelector("[value='Continue']")

  override def pageIsReady: Boolean = elementDisplayed(FirstName)

  def registerNewUser(): NewAccountConfirmationPage = {
    val user = User.newUser()

    textField(FirstName).value_=(user.firstName)
    textField(LastName).value_=(user.lastName)
    emailField(Email).value_=(user.email)
    telField(TelephoneNumber).value_=(user.telephone)
    pwdField(Password).value_=(user.password)
    pwdField(ConfirmPassword).value_=(user.password)
    checkbox(AgreeCheckBox).select()
    safeClick(ContinueButton)
    new NewAccountConfirmationPage
  }
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