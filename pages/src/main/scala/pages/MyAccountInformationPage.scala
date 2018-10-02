package pages

import org.openqa.selenium.WebDriver
import org.scalatest.selenium.WebBrowser.{textField, _}

class MyAccountInformationPage(implicit protected val webDriver: WebDriver) extends BrowserPage {

  override def pageIsReady: Boolean = pageTitle == "My Account Information"

  def personalDetails: PersonalDetails = {
    PersonalDetails(
      textField(id("input-firstname")).value,
      textField(id("input-lastname")).value,
      emailField(id("input-email")).value,
      telField(id("input-telephone")).value
    )
  }
}

case class PersonalDetails(
  firstName: String,
  lastName: String,
  email: String,
  telephone: String,
)

object PersonalDetails {
  def from(user: User): PersonalDetails =
    PersonalDetails(
      user.firstName,
      user.lastName,
      user.email,
      user.telephone
    )
}
