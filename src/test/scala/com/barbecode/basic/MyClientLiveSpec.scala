package com.barbecode.basic

import zio.*
import zio.test.*

object MyClientLiveSpec extends ZIOSpecDefault {
  override def spec: Spec[TestEnvironment & Scope, Any] = suite("Test client")(
    test("Call client with server down") {
      for {
        fiber         <- MyClient.callServer.fork
        _             <- TestClock.adjust(5.minutes)
        response      <- fiber.join
        consoleOutput <- TestConsole.output.map(_.last.trim)
      } yield assertTrue(response == "Fallback", consoleOutput == "Console fallback")
    },
    test("Call client with server up") {
      for {
        server        <- MyServer.runServer.fork
        fiber         <- MyClient.callServer.fork
        _             <- TestClock.adjust(5.minutes)
        response      <- fiber.join
        _             <- server.interrupt
        consoleOutput <- TestConsole.output.map(_.last.trim)
      } yield assertTrue(response == "Hello World!", consoleOutput == "Hello World!")
    },
  )
}
