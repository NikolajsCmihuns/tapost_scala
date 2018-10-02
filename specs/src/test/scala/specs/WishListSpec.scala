package specs

import pages._
import testkit.AcceptanceOneBrowserSpec
import testkit.StringUtils.uniqueString


class WishListSpec
  extends AcceptanceOneBrowserSpec
    with PersonalAreaSideMenuPage
    with WishListPage
    with CommonElements {


  "Wish list page" must {

    "allow to add/remove items in wish list" in {

      val loginUser = "wishlist@mail.com"
      val loginPass = "wishlist"

      Given("Opened wish list page")
      val topMenu = TopMenuPage.prepare
      val myAccountPage = topMenu
        .goToLoginPage()
        .login(loginUser, loginPass)
      topMenu.goToHomePage
      eventually(addToWishListButton.isDisplayed)
      click on addToWishListButton.underlying

      When("any item is added to wish list")
      topMenu.goToMyAccountPage

      And("wish list is opened")
      wishList.map(_.click())

      // header row is included also
      Then("wish list must not be empty")

      eventually(wishListItems.size mustBe 2)

      When("element is removed")
      eventually(click on removeButton.underlying)
      Then("wishlist must be empty")
      eventually(wishListItems mustBe empty)

    }
  }


}
