package browser

import java.util

import org.openqa.selenium.chrome.{ChromeDriver, ChromeOptions}
import org.openqa.selenium.{UnexpectedAlertBehaviour, WebDriver}

object CustomizedChromeDriver {

  def createWebDriver(): WebDriver = {

    val headlessChrome = sys.props.getOrElse("headless.chrome", "false").toBoolean
    val devToolsRequired = sys.props.getOrElse("devtools.chrome", "false").toBoolean

    val prefs = new util.HashMap[String, Boolean]
    prefs.put("credentials_enable_service", false)
    prefs.put("profile.password_manager_enabled", false)

    val options = new ChromeOptions()

    if (headlessChrome) {
      options.addArguments(
        "--headless",
        "--disable-gpu",
        "--window-size=1920x900"
      )
    }

    if (devToolsRequired) {
      options.addArguments("auto-open-devtools-for-tabs")
    }

    options
      .addArguments("--disable-infobars", "--start-fullscreen")
      .setExperimentalOption("prefs", prefs)
      .setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT)

    new ChromeDriver(options)
  }
}
