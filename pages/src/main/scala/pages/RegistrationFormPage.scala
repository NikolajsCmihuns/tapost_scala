package pages

import org.openqa.selenium.WebDriver
import org.scalatest.selenium.WebBrowser._
import testkit.StringUtils._
import testkit.WebPageUtils._

class RegistrationFormPage(implicit val webDriver: WebDriver) extends BrowserPage {

  override def pageIsReady: Any = elementDisplayed(ContinueButton)

  private val FirstName = textField("input-firstname")
  private val LastName = textField("input-lastname")
  private val Email = textField("input-email")
  private val TelephoneNumber = textField("input-telephone")
  private val Password = textField("input-password")
  private val ConfirmPassword = textField("input-confirm")
  private val SubscribeToNewsletter = radioButton("")
  private val AgreeCheckBox = checkbox("agree")
  private val ContinueButton = cssSelector("[value='Continue']")



  def registerNewUser() = {
    val user = User.newUser(false)

    FirstName.value_=(user.firstName)
    LastName.value_=(user.lastName)
    Email.value_=(user.email)
    TelephoneNumber.value_=(user.telephone)
    Password.value_=(user.password)
    ConfirmPassword.value_=(user.password)
    AgreeCheckBox.select()
    safeClick(ContinueButton)

  }
}

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