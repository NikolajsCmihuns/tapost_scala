package specs

import pages.TopMenuPage
import testkit.AcceptanceOneBrowserSpec

class UserRegistrationSpec extends AcceptanceOneBrowserSpec {


  "" must {

    "User must be able to create new account" in {


      Given("Registration page")
      val topMenu = TopMenuPage.prepare
      val registerPage = topMenu.goToRegisterPage()


      When("new user data is entered")
      val newAccountConfirmationPage = registerPage.registerNewUser()

      Then("user can logout")

      And("login againg")
    }
  }


}
