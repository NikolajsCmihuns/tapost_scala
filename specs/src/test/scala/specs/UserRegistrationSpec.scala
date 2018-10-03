package specs

import pages.{PersonalDetails, TopMenuPage, User}
import testkit.AcceptanceOneBrowserSpec
import testkit.WebPageUtils._

class UserRegistrationSpec extends AcceptanceOneBrowserSpec {


  "Users are able to create their accounts via email" must {

    "see registration form with mandatory fields" in {
      When("Registration page is opened")
      val topMenu = TopMenuPage.prepare
      val registerPage = topMenu.goToRegisterPage()

      Then("all mandatory fields are displayed")
      eventually {
        assert(
          Seq(
            registerPage.firtsNameField,
            registerPage.lastNameField,
            registerPage.emailInputField,
            registerPage.telephoneField).forall(e => elementDisplayed(e)),
          "All mandatory fields where not displayed"
        )
      }

      And("radio button is has NO value by default")
      assert(registerPage.subscribeNoRadio.isSelected)

      And("checkbox must be not selected by default")
      assert(!registerPage.agreeCheckBox.isSelected)

    }

    "be able to create new account and see registration info" in {

      Given("Registration page")
      val topMenu = TopMenuPage.prepare
      val registerPage = topMenu.goToRegisterPage()

      When("new user data is entered")
      val user = User.newUser()
      registerPage.register(user)

      Then("user can logout")
      topMenu.logout()

      And("user can login back")
      val myAccountPage = topMenu
        .goToLoginPage()
        .login(user.email, user.password)

      And("see information from registration")
      val myAccountInfoPage = myAccountPage.goToMyAccountInformationPage()
      eventually {
        myAccountInfoPage.personalDetails mustBe PersonalDetails.from(user)
      }
    }

    "not login without credentials" in {

      Given("Login page")
      val loginPage = TopMenuPage.prepare.goToLoginPage()

      When("user tries to login without entering credentials")
      loginPage.clickOnLoginButton()

      Then("alert message is displayed")
      waitUntilDisplayed(
        loginPage.alertMessage,
        "alert message was not displayed")

    }
  }
}
