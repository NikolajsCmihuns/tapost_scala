package pages


import org.openqa.selenium.{By, WebDriver, WebElement}
import org.scalatest.selenium.WebBrowser._
import testkit.WebPageUtils._

trait PersonalAreaSideMenuPage extends BrowserPage {

  // always true as this page will be mixed in
  override def pageIsReady: Boolean = true

  private def sideMenuSelector = id("column-right").element

  protected def myAccount: Option[WebElement] = underlyingWebElement(sideMenuSelector, By.linkText("My Account"))
  protected def editAccount: Option[WebElement] =
    underlyingWebElement(sideMenuSelector, By.linkText("Edit Account"))
  protected def password =
    underlyingWebElement(sideMenuSelector, By.linkText("Password"))
  // !!! this is a bug - address book displaying is not stated in requirements
  protected def addressBook =
    underlyingWebElement(sideMenuSelector, By.linkText("Address Book"))
  protected def wishList =
    underlyingWebElement(sideMenuSelector, By.linkText("Wish List"))
  protected def orderHistory =
    underlyingWebElement(sideMenuSelector, By.linkText("Order History"))
  protected def downloads = linkText("Downloads")
  protected def returns = linkText("Returns")
  protected def logout = linkText("Logout")
  protected def newsLetter = linkText("Newsletter")
  protected def rewardPoints = linkText("Reward Points")
  protected def reccuringPayments = linkText("Reccuring payments")
  protected def transactions = linkText("Transactions")

}
