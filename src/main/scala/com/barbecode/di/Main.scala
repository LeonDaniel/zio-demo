package com.barbecode.di

import zio.*

import java.io.IOException

object Main extends ZIOAppDefault {

  private case class Config(server: String, port: Int)

  private val zioConfig: ZIO[Config, IOException, String] =
    for {
      server <- ZIO.serviceWith[Config](_.server)
      port   <- ZIO.serviceWith[Config](_.port)
      _      <- Console.printLine(s"Passed $server:$port")
    } yield s"Server: $server, port: $port"

  // later provide the config

  override def run = zioConfig.provide(ZLayer.succeed(Config("localhost", 8080)))

}
