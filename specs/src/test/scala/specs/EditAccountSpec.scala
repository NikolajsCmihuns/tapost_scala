package specs

import pages.{EditProfilePage, PersonalAreaSideMenuPage, TopMenuPage, User}
import testkit.AcceptanceOneBrowserSpec
import org.scalatest.selenium.WebBrowser._
import testkit.StringUtils.uniqueString
import testkit.WebPageUtils._


class EditAccountSpec
  extends AcceptanceOneBrowserSpec
    with PersonalAreaSideMenuPage {


  "Edit account page" must {

    "allow to edit user account" in {

      val loginUser = "firstEvo1@abc1.com"
      val loginPass = "FirstEvo1Pass"

      val newEmail = s"${uniqueString(5)}@cde.com"
      val newFirstName = uniqueString(10)
      val newLastName = uniqueString(10)
      val newPhone = uniqueString(10)

      Given("Opened edit account page")
      val topMenu = TopMenuPage.prepare
      val myAccountPage = topMenu
        .goToLoginPage()
        .login(loginUser, loginPass)

      editAccount.map(_.click())

      def profilePage = new EditProfilePage()

      When("name, last name, email and phone is changed")
      profilePage.updateUserFirstName(newFirstName)
      profilePage.updateUserLastName(newLastName)
      profilePage.updateUserEmail(newEmail)
      profilePage.updateUserPhone(newPhone)

      And("continue button is clicked")
      click on profilePage.continueButtonQuery.element.underlying
      eventually(profilePage.alertText mustBe "Success: Your account has been successfully updated.")
      Then("message about changes successfully done is displayed")

      When("navigate to edit form again")
      editAccount.map(_.click())
      Then("new values are displayed in form")

      profilePage.getFirstNameValue mustBe newFirstName
      profilePage.getLastNameValue mustBe newLastName
      profilePage.getEmailValue mustBe newEmail
      profilePage.getPhoneNumberValue mustBe newPhone

    }
  }


}
