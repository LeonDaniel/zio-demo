package com.barbecode.di

import zio.{ ZIO, ZLayer }

trait Cache {
  def getUser(id: String): ZIO[Any, Throwable, User]
}

object Cache {
  def getUser(id: String): ZIO[Cache, Throwable, User] = ZIO.serviceWithZIO[Cache](_.getUser(id))
}

case class CacheLive(persistence: Persistence) extends Cache {
  override def getUser(id: String): ZIO[Any, Throwable, User] =
    persistence.getUser(id).catchAll(_ => ZIO.succeed(User(id = id, name = "Default user", age = 30)))
}

object CacheLive {
  val layer: ZLayer[Persistence, Nothing, Cache] = ZLayer.fromFunction(CacheLive(_))
}
