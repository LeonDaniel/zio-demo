package com.barbecode.basic

import zio.*
import zio.http.*
import zio.http.model.Method

object MyServer {
  private val app: HttpApp[Any, Nothing] = Http.collect[Request] {
    case Method.GET -> _ / "text" => Response.text("Hello World!")
  }

  def runServer: ZIO[Any, Throwable, Unit] =
    Server.serve(app).provide(Server.default)
}
