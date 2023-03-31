package com.barbecode.di

import zio.*

trait Logic {
  def canUserDrink(id: String): ZIO[Any, Nothing, Boolean]
}

object Logic {
  def canUserDrink(id: String): ZIO[Logic, Nothing, Boolean] = ZIO.serviceWithZIO[Logic](_.canUserDrink(id))
}

case class LogicLive(cache: Cache) extends Logic {
  override def canUserDrink(id: String): ZIO[Any, Nothing, Boolean] =
    cache.getUser(id).map(_.age > 18).catchAll(_ => ZIO.succeed(false))
}

object LogicLive {
  val layer: ZLayer[Cache, Nothing, Logic] = ZLayer.fromFunction(LogicLive(_))
}
