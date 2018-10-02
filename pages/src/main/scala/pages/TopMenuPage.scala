package pages

import TopMenuPage._
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.scalatest.selenium.WebBrowser._
import testkit.WebPageUtils._

class TopMenuPage(implicit protected val webDriver: WebDriver) extends BrowserPage {

  private val MyAccount = linkText("My Account")
  private val Register = linkText("Register")
  private val Logout = linkText("Logout")
  private val Login = linkText("Login")

  def goToHomePage = go to MainPage.url
  def goToMyAccountPage = go to MainPage.url + "/index.php?route=account/account"

  override def pageIsReady: Boolean = true

  def goToRegisterPage(): RegistrationFormPage = {
    safeClick(MyAccount)
    safeClick(Register)
    new RegistrationFormPage
  }
  
  def products: Seq[Product] =
    tableRowsFromRootElement(table.element, By.tagName("div")) { element =>
      Product(
        url = element.findElement(By.xpath("/div[@class='image']/a")).getText,
        name = element.findElement(By.xpath("/div[@class='caption']")).getText,
        description = "",
        price = "",
        priceWithoutTax = ""
      )
    }

  def goToLoginPage(): AccountLoginPage = {
    safeClick(MyAccount)
    safeClick(Login)
    new AccountLoginPage
  }

  def goToPCproductPage(): PCproductPage = {
    safeClick(linkText("Desktops"))
    safeClick(linkText("Show All Desktops"))
    new PCproductPage
  }

  def logout(): AccountLogoutPage = {
    safeClick(MyAccount)
    safeClick(Logout)
    new AccountLogoutPage
  }

  private def addToCart(productName: String) =
    xpath(s"${row(productName).queryString}//button[@data-original-title='Update']")

  private def table =
    xpath("//div[@id='content']//div[contains(@class,'product-thumb')]")
  private def row(productName: String) =
    xpath(s"${table.queryString}/")

}

object TopMenuPage {
  
  def prepare(implicit webDriver: WebDriver): TopMenuPage = {
    go to MainPage.url
    new TopMenuPage
  }
  
  case class Product(
    url: String,
    name: String,
    description: String,
    price: String,
    priceWithoutTax: String
  )
}
