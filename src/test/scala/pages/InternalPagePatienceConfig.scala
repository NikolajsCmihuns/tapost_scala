package pages

import org.scalatest.concurrent.PatienceConfiguration
import org.scalatest.time.{Millis, Seconds, Span}

trait InternalPagePatienceConfig extends PatienceConfiguration {

  private val defaultPatienceConfig: PatienceConfig =
    PatienceConfig(
      timeout = scaled(Span(15, Seconds)),
      interval = scaled(Span(500, Millis))
    )

  implicit abstract override val patienceConfig: PatienceConfig = defaultPatienceConfig
}
