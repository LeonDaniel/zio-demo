package com.barbecode.di

import zio.{ ZIO, ZLayer }

case class User(
    id: String,
    name: String,
    age: Int,
  )

trait Persistence {
  def saveUser(user: User): ZIO[Any, Throwable, Unit]
  def getUser(id: String): ZIO[Any, Throwable, User]
}

case class PersistenceLive() extends Persistence {
  override def saveUser(user: User): ZIO[Any, Throwable, Unit] = ZIO.logInfo(s"User $user is saved")

  override def getUser(id: String): ZIO[Any, Throwable, User] = ZIO.fail(new Exception("Could not access db"))
}

object PersistenceLive {
  val layer = ZLayer.succeed(PersistenceLive())
}
