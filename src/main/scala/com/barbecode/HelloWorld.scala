package com.barbecode

import zio.*
import zio.http.*
import zio.http.model.Method

object HelloWorld extends ZIOAppDefault {

  val app: HttpApp[Any, Nothing] = Http.collect[Request] {
    case Method.GET -> _ / "text" => Response.text("Hello World!")
  }

  override val run =
    Server.serve(app).provide(Server.default)
}
