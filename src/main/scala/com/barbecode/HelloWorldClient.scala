package com.barbecode

import zio.*
import zio.http.Client
import zio.http.model.Headers

object HelloWorldClient extends ZIOAppDefault:
  val url = "http://localhost:8080/text"

  val request: ZIO[Client, Throwable, Unit] = for {
    res  <- Client.request(url)
    data <- res.body.asString
    _    <- Console.printLine(data)
  } yield ()

  val program = request
    .tapError(_ => Console.printLine("Failed request"))
    .retry(Schedule.exponential(10.milliseconds) && Schedule.recurs(5))
    .orElse(Console.printLine("Fallback"))


  override val run = program.provide(Client.default).exitCode
