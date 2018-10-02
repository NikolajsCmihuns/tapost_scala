package pages


import org.openqa.selenium.{By, WebDriver, WebElement}
import org.scalatest.selenium.WebBrowser._
import testkit.WebPageUtils._

trait PersonalAreaSideMenuPage extends BrowserPage {

  // always true as this page will be mixed in
  override def pageIsReady: Boolean = true

  private def sideMenuSelector = id("column-right").element

  private def myAccount: Option[WebElement] = underlyingWebElement(sideMenuSelector, By.linkText("My Account"))
  protected def editAccount: Option[WebElement] =
    underlyingWebElement(sideMenuSelector, By.linkText("Edit Account"))
  private def password =
    underlyingWebElement(sideMenuSelector, By.linkText("Password"))
  // !!! this is a bug - address book displaying is not stated in requirements
  private def addressBook =
    underlyingWebElement(sideMenuSelector, By.linkText("Address Book"))
  private def wishList =
    underlyingWebElement(sideMenuSelector, By.linkText("Wish List"))
  private def orderHistory =
    underlyingWebElement(sideMenuSelector, By.linkText("Order History"))
  private def downloads = linkText("Downloads")
  private def returns = linkText("Returns")
  private def logout = linkText("Logout")
  private def newsLetter = linkText("Newsletter")
  private def rewardPoints = linkText("Reward Points")
  private def reccuringPayments = linkText("Reccuring payments")
  private def transactions = linkText("Transactions")

}
