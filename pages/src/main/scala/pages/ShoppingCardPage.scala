package pages

import ShoppingCardPage._
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.scalatest.selenium.WebBrowser._
import scala.collection.JavaConverters._
import testkit.WebPageUtils._

class ShoppingCardPage(implicit val webDriver: WebDriver) extends BrowserPage {
  
  override def pageIsReady: Boolean = true
  
  private def table =
    xpath("//div[@id='content']/form//tbody")
  private def row(productName: String) =
    xpath(s"${table.queryString}/tr[td/a[.='$productName']]")
  private def quantityField(productName: String) =
    xpath(s"${row(productName).queryString}/input")
  private def updateQuantityButton(productName: String) =
    xpath(s"${row(productName).queryString}//button[@data-original-title='Update']")
  private def removeButton(productName: String) =
    xpath(s"${row(productName).queryString}//button[@data-original-title='Remove']")
    
  def products: Seq[Product] =
    tableRowsFromRootElement(table.element, By.tagName("tr")) { element =>

      val cells = element.findElements(By.tagName("td")).asScala

      Product(
        url = cells(0).getText,
        imageURL = cells(1).getText,
        name = cells(2).getText,
        rewardPoints = cells(3).getText,
        model = cells(4).getText,
        quantity = cells(5).getText,
        unitPrice = cells(6).getText,
        total = cells(7).getText
      )
    }
  
  def updateQuantity(productName: String, value: String): ShoppingCardPage = {
    textField(quantityField(productName)).value = value
    click on updateQuantityButton(productName)
    this
  }
    
  def removeProduct(productName: String, value: String): ShoppingCardPage = {
    click on removeButton(productName)
    this
  }
  
  def applyCoupon(code: String): ShoppingCardPage = ???
  def getQuotes(country: String, region: String, postCode: String): ShoppingCardPage = ???
  def applyGift(code: String): ShoppingCardPage = ???
  
  def subtotal: String = ???
  def shippingName: String = ???
  def shippingCost: String = ???
  
  def continueShopping: BrowserPage = ???
  def checkout: Either[BrowserPage, ShoppingCardPage] = ???
  
}
object ShoppingCardPage {
  
  case class Notification(`type`: String, message: String)
  
  case class Product(
    url: String,
    imageURL: String,
    name: String,
    rewardPoints: String,
    model: String,
    quantity: String,
    unitPrice: String,
    total: String
  )
  
}