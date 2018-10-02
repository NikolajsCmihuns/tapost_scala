package specs

import pages.{CommonElements, EditProfilePage, PersonalAreaSideMenuPage, TopMenuPage}
import testkit.AcceptanceOneBrowserSpec
import org.scalatest.selenium.WebBrowser._
import testkit.StringUtils.uniqueString
import testkit.WebPageUtils._


class EditAccountSpec
  extends AcceptanceOneBrowserSpec
    with PersonalAreaSideMenuPage
    with CommonElements {


  "Edit account page" must {

    "allow to edit user account" in {

      val loginUser = "49ECC@cde.com"
      val loginPass = "FirstEvo1Pass"

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

      When("name, last name and phone is changed")
      profilePage.updateUserFirstName(newFirstName)
      profilePage.updateUserLastName(newLastName)
      profilePage.updateUserPhone(newPhone)

      And("continue button is clicked")
      click on continueButtonQuery.element.underlying
      eventually(alertText.text must include("Success"))
      Then("message about changes successfully done is displayed")

      When("navigate to edit form again")
      editAccount.map(_.click())
      Then("new values are displayed in form")

      profilePage.getFirstNameValue mustBe newFirstName
      profilePage.getLastNameValue mustBe newLastName
      profilePage.getPhoneNumberValue mustBe newPhone

    }
  }


}
