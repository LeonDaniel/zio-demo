package com.barbecode

import com.dimafeng.testcontainers.KafkaContainer
import io.github.scottweaver.zio.testcontainers.kafka.ZKafkaContainer
import zio.*
import zio.test.*

object KafkaSpec extends ZIOSpecDefault {
  override def spec: Spec[TestEnvironment & Scope, Any] = suite("Integration suite with Kafka container")(
    test("Extract consumer settings") {
      for {
        kc  <- ZIO.service[KafkaContainer]
        host = kc.host
      } yield assertTrue(host == "localhost")
    }
  ).provideSomeShared[Scope](
    ZKafkaContainer.Settings.default,
    ZKafkaContainer.live,
  ) @@ TestAspect.withLiveClock @@ TestAspect.timeout(20.seconds)
}
