package pages

import org.openqa.selenium.{By, WebElement}
import org.scalatest.selenium.WebBrowser._
import testkit.WebPageUtils._

trait WishListPage extends BrowserPage {

  def wishListItems = cssSelector(".table-responsive tr").findAllElements.toList

}
