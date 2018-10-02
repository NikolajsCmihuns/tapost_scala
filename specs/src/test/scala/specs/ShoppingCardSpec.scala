package specs

import pages.{TopMenuPage, User}
import testkit.AcceptanceOneBrowserSpec

class ShoppingCardSpec extends AcceptanceOneBrowserSpec {

  "User shopping card page" must {

    "be able to add one product to shopping card" in {

      val topMenuPage = TopMenuPage.prepare
      val loginPage = topMenuPage.goToLoginPage()
      val myAccountPage = loginPage.login(user.email, user.password)

      topMenuPage.goToLoginPage()

    }

  }

  val user = User(
    "FirstEvo3",
    "LastEvo3",
    "firstEvo1@abc3.com",
    "+371 22224444",
    "FirstEvo3Pass",
    subscribeToNewsLetter = false)
}
