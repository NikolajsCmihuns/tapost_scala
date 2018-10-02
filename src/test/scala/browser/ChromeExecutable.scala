package browsers

import io.github.bonigarcia.wdm.WebDriverManager

trait ChromeExecutable {

  WebDriverManager
    .config()
    .setTargetPath("~/.ivy2/repository/webdriver")
  WebDriverManager
    .chromedriver()
    .version("2.35")
    .setup()

}

