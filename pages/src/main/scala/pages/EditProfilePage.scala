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

  def getFirstNameValue = firstName.element.attribute("value").getOrElse("")
  def getLastNameValue = lastName.element.attribute("value").getOrElse("")
  def getEmailValue = email.element.attribute("value").getOrElse("")
  def getPhoneNumberValue = telephoneNumber.element.attribute("value").getOrElse("")

  def updateUserFirstName(newName: String) = textField(firstName).value_=(newName)
  def updateUserLastName(newLastName: String) = textField(lastName).value_=(newLastName)
  def updateUserEmail(newEmail: String) = emailField(email).value_=(newEmail)
  def updateUserPhone(newPhone: String) = telField(telephoneNumber).value_=(newPhone)

}