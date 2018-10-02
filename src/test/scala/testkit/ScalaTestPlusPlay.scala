package testkit

import org.scalatestplus.play.ServerProvider
import org.scalatestplus.play.guice.GuiceFakeApplicationFactory
import play.api.Application

trait ScalaTestPlusPlay extends ServerProvider with GuiceFakeApplicationFactory {

  override implicit def app: Application = fakeApplication()
  override def port = 0

}

