package pages
import org.openqa.selenium.{By, WebDriver}
import org.scalatest.selenium.WebBrowser._
import testkit.WebPageUtils._

class PCproductPage(implicit protected val webDriver: WebDriver) extends BrowserPage {

  override def pageIsReady: Boolean = pageTitle == "Desktops"

  def getAllProducts(): Seq[DesktopProduct] = {
    tableRowsBySelector(cssSelector(".product-layout"))(createDesktopProduct)
  }

  private def createDesktopProduct(element: Element) = {
    def value(css: String) = underlyingElementText(element, By.cssSelector(css))
    DesktopProduct(
      name = value("h4 a"),
      priceNew = value(".price-new"),
      priceOld = value(".price-old"),
      priceTax = value(".price-tax")
    )
  }
}

case class DesktopProduct(
  name: String,
  priceNew: String,
  priceOld: String,
  priceTax: String
) {

  def addToCart = {

  }

}