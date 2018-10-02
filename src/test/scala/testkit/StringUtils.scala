package testkit

import java.util.UUID

object StringUtils {

  def uniqueString(size: Int = 36): String =
    UUID.randomUUID.toString.toUpperCase.replaceAll("-", "") take size

}
