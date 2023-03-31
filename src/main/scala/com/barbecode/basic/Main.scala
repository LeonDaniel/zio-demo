package com.barbecode.basic

import zio.*

import java.io.IOException

val program: ZIO[Any, IOException, Int] = for {
  number <- ZIO.succeed(42)
  random <- Random.nextIntBetween(0, 2)
  sum     = number + random
  _      <- Console.printLine(sum)
} yield sum
object Main extends App {
  scala.Console.println("Hello Scala 3")
  scala.Console.println(program)

}

object ZIOApp extends ZIOAppDefault {
  override def run: ZIO[Any & ZIOAppArgs & Scope, Any, Any] = program
}
