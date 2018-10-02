package pages

import org.openqa.selenium.WebDriver
import org.scalatest.selenium.WebBrowser._
import testkit.StringUtils._
import testkit.WebPageUtils._

class EditProfilePage(implicit protected val webDriver: WebDriver) extends BrowserPage {

  override def pageIsReady: Boolean = elementDisplayed(firstName)

  private def firstName = id("input-firstname")
  private def lastName = id("input-lastname")
  private def email = id("input-email")
  private def telephoneNumber = id("input-telephone")

  def getFirstNameValue = firstName.element.text
  def getLastNameValue = lastName.element.text
  def getEmailValue = email.element.text
  def getPhoneNumberValue = telephoneNumber.element.text

  def updateUserFirstName(newName: String) = textField(firstName).value_=(newName)
  def updateUserLastName(newLastName: String) = textField(lastName).value_=(newLastName)
  def updateUserEmail(newEmail: String) = emailField(email).value_=(newEmail)
  def updateUserPhone(newPhone: String) = telField(telephoneNumber).value_=(newPhone)

  // todo: this has to be generic in some future which probably will not come
  def continueButtonQuery = cssSelector("[value='Continue']")
  def alertText = cssSelector(".alert-success.alert-dismissible")

}