package testkit

import com.github.t3hnar.scalax.StringOption
import com.typesafe.scalalogging.LazyLogging
import org.openqa.selenium.{By, WebDriver, WebElement}
import org.scalactic.source.Position
import org.scalatest.Assertions
import org.scalatest.concurrent.Eventually
import org.scalatest.concurrent.PatienceConfiguration.{Interval, Timeout}
import org.scalatest.exceptions.TestFailedException
import org.scalatest.selenium.WebBrowser
import org.scalatest.selenium.WebBrowser._
import org.scalatest.time.{Millis, Seconds, Span}

import scala.collection.JavaConverters._
import scala.util.Try

object WebPageUtils extends Eventually with SpecPatienceConfig with LazyLogging with Assertions {

  def elementText(query: Query)(implicit webDriver: WebDriver): String = query.element.text.trim

  def elementText(element: Element): String = element.text.trim

  def webElementOptionalText(webElement: WebElement): Option[String] = StringOption(webElement.getText.trim)

  def eachElementAttributeValue(query: Query, attributeName: String)(implicit webDriver: WebDriver): Seq[String] = eventually {
    query.findAllElements.toSeq.map(elem => elementAttributeValue(elem, attributeName))
  }

  def elementAttributeValue(element: Element, attributeName: String): String = {
    element.attribute(attributeName).getOrElse("")
  }

  def webElementAttributeValue(webElement: WebElement, attributeName: String): String = {
    Option(webElement.getAttribute(attributeName)).getOrElse("")
  }

  def underlyingElementText(element: Element, subElement: By): String = {
    element.underlying.findElement(subElement).getText.trim
  }

  def underlyingElementOptionalText(element: Element, subElement: By): Option[String] = {
    StringOption(underlyingElementText(element, subElement))
  }

  def eachElementText(query: Query)(implicit webDriver: WebDriver): Seq[String] = {
    query.findAllElements.toList.map(elementText)
  }

  def eachUnderlyingElementText(element: Element, subElement: By): Seq[String] = {
    element.underlying.findElements(subElement).asScala.map(_.getText.trim)
  }

  def underlyingOptionalElementText(element: Element, subElement: By): Option[String] = {
    val result = element.underlying.findElements(subElement).asScala.toList
    selectFirstAsOption(result)
  }

  def underlyingWebElement(element: Element, subElement: By): Option[WebElement] = {
    Try(element.underlying.findElement(subElement)).toOption
  }

  def underlyingWebElementText(webElement: WebElement, subElement: By): String = {
    webElement.findElement(subElement).getText.trim
  }

  def underlyingOptionalWebElementText(webElement: WebElement, subElement: By): Option[String] = {
    val result = webElement.findElements(subElement).asScala.toList
    selectFirstAsOption(result)
  }

  private def selectFirstAsOption(webElements: Seq[WebElement]): Option[String] = {
    webElements match {
      case Nil           => None
      case single :: Nil => Some(single.getText.trim())
      case _             => sys.error(s" more than one underlying elements were found")
    }
  }

  def optionalElementText(query: Query)(implicit webDriver: WebDriver): Option[String] = {
    find(query).map(_.text.trim)
  }

  def elementDisplayed(query: Query)(implicit webDriver: WebDriver): Boolean = {
    elementDisplayed(query.element)
  }

  def elementDisplayed(element: => Element): Boolean = {
    isDisplayedWithCatchingExceptions(element.isDisplayed)
  }

  def elementDisplayed(webElement: WebElement): Boolean = {
    isDisplayedWithCatchingExceptions(webElement.isDisplayed)
  }

  def elementDefined(query: Query)(implicit webDriver: WebDriver): Boolean = {
    query.findElement match {
      case Some(_) => true
      case None    => false
    }
  }

  def elementIsNotMoving(element: => Element): Boolean = {
    val initialLocation = element.location
    Try(Thread.sleep(200))
    val finalLocation = element.location
    initialLocation == finalLocation
  }

  def underlyingCheckboxSelected(element: Element): Boolean = {
    element.underlying.findElement(By.cssSelector("input[type='checkbox']")).isSelected
  }

  def underlyingElementAttributeValue(element: Element, nestedElementLocator: By, attributeName: String): String = {
    element.underlying.findElement(nestedElementLocator).getAttribute(attributeName)
  }

  def underlyingElementExists(element: Element, subElement: By): Boolean = {
    Try(element.underlying.findElement(subElement)).fold(
      _ => false,
      _ => true
    )
  }

  def tableRowsBySelector[T](rowSelector: Query)(rowTransform: Element => T)(implicit webDriver: WebDriver): Seq[T] = {
    rowSelector.findAllElements.toSeq.map(rowTransform)
  }

  def tableRowsFromRootElement[T](rootElement: Element, subElements: By)(transform: WebElement => T): Seq[T] = {
    rootElement.underlying.findElements(subElements).asScala.map(transform)
  }

  def safeJSClick(query: Query)(implicit webDriver: WebDriver, patienceConfig: PatienceConfig): Unit = {
    val elem = waitUntilExistsInDOMAndReturn(query)
    executeScript(s"arguments[0].click()", elem.underlying)
  }

  def safeClick(query: Query)(implicit webDriver: WebDriver): Unit = {
    Try(clickAfterDisplayedAndEnabled(query)(webDriver, shortPatienceConfig)).failed foreach {
      case e: org.openqa.selenium.WebDriverException if otherElementReceivedAClick(e) =>
        logger.error(s"SAFE_CLICK_QUERY: other element received a click", e)
        Console.err.println(s"SAFE_CLICK_QUERY: other element received a click", e)
        waitUntil(
          {
            val copyOfTargetOrNestedElement = getWebElementFromExceptionPoints(e).get
            val targetElement = query.element.underlying
            copyOfTargetOrNestedElement.equals(targetElement) ||
              targetElement.findElements(By.cssSelector("*")).contains(copyOfTargetOrNestedElement)
          },
          s"SAFE_CLICK_QUERY: Failed to wait until query $query becomes available"
        )
        clickWithRetries(query)
      case e: org.openqa.selenium.StaleElementReferenceException                      =>
        logger.error(s"SAFE_CLICK_QUERY: $query was stale element", e)
        clickWithRetries(query)
      case e: Throwable                                                               =>
        logger.error(s"SAFE_CLICK_QUERY: default case", e)
        clickWithRetries(query)
    }
  }

  def safeClick(element: Element)(implicit webDriver: WebDriver): Unit = {
    Try(clickAfterDisplayedAndEnabled(element)(shortPatienceConfig)).failed foreach {
      case e: org.openqa.selenium.WebDriverException if otherElementReceivedAClick(e) =>
        logger.error(s"SAFE_CLICK_ELEMENT: other element received a click", e)
        waitUntil(
          {
            val copyOfTargetOrNestedElement = getWebElementFromExceptionPoints(e).get
            val targetElement = element.underlying
            copyOfTargetOrNestedElement.equals(targetElement) ||
              targetElement.findElements(By.cssSelector("*")).contains(copyOfTargetOrNestedElement)
          }
          , s"SAFE_CLICK_ELEMENT: Failed to wait until $element becomes available"
        )
        clickWithRetries(element)
      case e: Throwable                                                               =>
        logger.error(s"SAFE_CLICK_ELEMENT: default case", e)
        clickWithRetries(element)
    }
  }

  def safeClick(webElement: WebElement)(implicit webDriver: WebDriver): Unit = {
    Try(clickAfterDisplayedAndEnabled(webElement)(shortPatienceConfig)).failed foreach {
      case e: org.openqa.selenium.WebDriverException if otherElementReceivedAClick(e) =>
        logger.error(s"SAFE_CLICK_WEB_ELEMENT: other element received a click", e)
        waitUntil(
          {
            val copyOfTargetOrNestedElement = getWebElementFromExceptionPoints(e).get
            copyOfTargetOrNestedElement.equals(webElement) ||
              webElement.findElements(By.cssSelector("*")).contains(copyOfTargetOrNestedElement)
          }
          , s"SAFE_CLICK_WEB_ELEMENT: Failed to wait until $webElement becomes available"
        )
        clickWithRetries(webElement)
      case e: Throwable                                                               =>
        logger.error(s"SAFE_CLICK_WEB_ELEMENT: default case", e)
        clickWithRetries(webElement)
    }
  }

  def scrollIntoView(webElement: WebElement, alignToTop: Boolean = true)(implicit webDriver: WebDriver): Unit = {
    executeScript(s"arguments[0].scrollIntoView($alignToTop)", webElement)
  }

  def scrollUpPage()(implicit webDriver: WebDriver): Unit = executeScript("window.scrollTo(0, 0)")

  def waitUntilDisplayed(query: Query, errorMessage: String)(implicit webDriver: WebDriver, patienceConfig: PatienceConfig): Unit = {
    waitUntil(elementDisplayed(query), errorMessage)
  }

  def waitUntilDisplayed(element: Element, errorMessage: String)(implicit patienceConfig: PatienceConfig): Unit = {
    waitUntil(elementDisplayed(element), errorMessage)
  }

  def waitUntilDisplayedAndReturn[Q <: WebBrowser.Query](query: Q, errorMessage: String)(implicit webDriver: WebDriver, patienceConfig: PatienceConfig): Element = {
    eventually(Timeout(patienceConfig.timeout), Interval(patienceConfig.interval)) {
      val element = query.element
      assert(elementDisplayed(element), errorMessage)
      element
    }
  }

  def waitUntilExistsInDOMAndReturn(query: Query)(implicit webDriver: WebDriver, patienceConfig: PatienceConfig): Element = {
    eventually(Timeout(patienceConfig.timeout), Interval(patienceConfig.interval)) {
      query.element
    }
  }

  def waitUntilDisplayedAndEnabled(query: Query)(implicit webDriver: WebDriver, patienceConfig: PatienceConfig): Unit = {
    eventually(Timeout(patienceConfig.timeout), Interval(patienceConfig.interval)) {
      val element = query.element
      assert(elementDisplayed(element), s"$query element was not displayed")
      assert(element.isEnabled, s"$query element was not enabled")
    }
  }

  def waitUntilDisplayedAndEnabledAndReturn(query: Query)(implicit webDriver: WebDriver, patienceConfig: PatienceConfig): Element = {
    eventually(Timeout(patienceConfig.timeout), Interval(patienceConfig.interval)) {
      val element = query.element
      assert(elementDisplayed(element), s"$query element was not displayed")
      assert(element.isEnabled, s"$query element was not enabled")
      element
    }
  }

  def waitUntil(predicate: => Boolean, message: String)(implicit patienceConfig: PatienceConfig): Unit = {
    eventually(Timeout(patienceConfig.timeout), Interval(patienceConfig.interval)) {
      assert(predicate, message)
    }
  }

  private def otherElementReceivedAClick(e: org.openqa.selenium.WebDriverException): Boolean = {
    e.getMessage.contains("Other element would receive the click")
  }

  private def clickAfterDisplayedAndEnabled(element: Element)(implicit patienceConfig: PatienceConfig): Unit = {
    waitUntil(element.isDisplayed, s"$element is not displayed")
    waitUntil(element.isEnabled, s"$element is not enabled")
    click on element
  }

  private def clickAfterDisplayedAndEnabled(webElement: WebElement)(implicit patienceConfig: PatienceConfig): Unit = {
    waitUntil(webElement.isDisplayed, s"$webElement is not displayed")
    waitUntil(webElement.isEnabled, s"$webElement is not enabled")
    webElement.click()
  }

  private def clickAfterDisplayedAndEnabled(query: Query)(implicit webDriver: WebDriver, patienceConfig: PatienceConfig): Unit = {
    val element: Element = waitUntilDisplayedAndEnabledAndReturn(query)
    click on element
  }

  private val shortPatienceConfig = PatienceConfig(
    timeout = scaled(Span(5, Seconds)),
    interval = scaled(Span(500, Millis))
  )

  private val extendedPatienceConfig: PatienceConfig = PatienceConfig(
    timeout = scaled(Span(5, Seconds)),
    interval = scaled(Span(500, Millis))
  )

  private def clickWithRetries(query: Query)(implicit webDriver: WebDriver): Unit = {
    eventually {
      logger.error(s"Retrying click on $query")

      assert(elementDisplayed(query), s"$query element was not displayed")
      assert(query.element.isEnabled, s"$query element was not enabled")

      click on query
    }(extendedPatienceConfig, Position.here)
  }

  private def clickWithRetries(element: Element): Unit = {
    eventually {

      assert(elementDisplayed(element), Try(s"$element was not displayed").getOrElse("element was not displayed"))
      assert(element.isEnabled, Try(s"$element was not enabled").getOrElse("element was not enabled"))

      click on element
    }(extendedPatienceConfig, Position.here)
  }

  private def clickWithRetries(webElement: WebElement): Unit = {
    eventually {

      assert(elementDisplayed(webElement), Try(s"$webElement was not displayed").getOrElse("webElement was not displayed"))
      assert(webElement.isEnabled, Try(s"$webElement was not enabled").getOrElse("webElement was not enabled"))

      click on webElement
    }(extendedPatienceConfig, Position.here)
  }

  private def isDisplayedWithCatchingExceptions(f: => Boolean): Boolean = {
    try {
      f
    } catch {
      case _: TestFailedException                                => false
      case _: org.openqa.selenium.NoSuchElementException         => false
      case _: org.openqa.selenium.StaleElementReferenceException => false
    }
  }

  private def getWebElementFromExceptionPoints(e: org.openqa.selenium.WebDriverException)(implicit webDriver: WebDriver): Option[WebElement] = {
    val pointsPattern = """(\d+), (\d+)""".r
    val pointsString = pointsPattern.findFirstIn(e.getMessage) match {
      case Some(points) => points
      case None         => throw new RuntimeException("Exception message did not contain points information", e)
    }
    val splitPoints = pointsString.split(", ").map(_.trim.toInt)
    val x = splitPoints.head
    val y = splitPoints(1)
    Option(executeScript(s"return document.elementFromPoint($x, $y);").asInstanceOf[WebElement])
  }
}
