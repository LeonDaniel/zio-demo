package com.barbecode.di

import zio.*

object App extends ZIOAppDefault {

  override def run: ZIO[Environment & ZIOAppArgs & Scope, Any, Any] =
    Logic.canUserDrink("1").provide(LogicLive.layer, PersistenceLive.layer, CacheLive.layer).exit
}
