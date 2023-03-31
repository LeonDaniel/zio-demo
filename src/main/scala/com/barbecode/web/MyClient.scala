package com.barbecode.web

import MainClient.request
import zio.*
import zio.http.Client

object MyClient {
  private val url = "http://localhost:8080/text"

  private val request: ZIO[Client, Throwable, String] = for {
    res  <- Client.request(url)
    data <- res.body.asString
    _    <- Console.printLine(data)
  } yield data

  def callServer: ZIO[Any, Throwable, String] =
    request
      .tapError(_ => Console.printLine("Failed request"))
      .retry(Schedule.exponential(10.milliseconds) && Schedule.recurs(5))
      .orElse(Console.printLine("Console fallback").zipRight(ZIO.succeed("Fallback")))
      .provide(Client.default)
}
