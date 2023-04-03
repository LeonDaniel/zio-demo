package com.barbecode.web

import zio.*
import zio.http.*
import zio.http.model.Method

object MainServer extends ZIOAppDefault {

  val app: HttpApp[Any, Nothing] = Http.collect[Request] {
    case Method.GET -> _ / "text" => Response.text("Hello World!")
  }

  override val run: ZIO[ZIOAppArgs & Scope, Any, Any] = {
    val io: ZIO[Server, Nothing, Nothing] = Server.serve(app)
    val f: ZIO[Any, Throwable, Nothing]   = io.provide(Server.default)
    f
  }
}
