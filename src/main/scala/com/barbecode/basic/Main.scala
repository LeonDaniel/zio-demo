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

  // the ZIO runtime is the one running the effect
  Unsafe.unsafe { implicit unsafe =>
    Runtime.default.unsafe.run(program)
  }

}

object ZIOApp extends ZIOAppDefault {
  override def run: ZIO[Any & ZIOAppArgs & Scope, Any, Any] = program
}

object Test extends ZIOAppDefault {
  val program: ZIO[Any, Throwable, Unit] =
    for {
      s   <- Console.readLine("What's your age?\n")
      age <- ZIO.attempt(s.toInt)
      _   <- ZIO.fail(new Exception("You are not allowed to be here")).when(age < 18)
      _   <- Console.printLine("Welcome!")
    } yield ()

  override def run: ZIO[Environment & ZIOAppArgs & Scope, Any, Any] =
    val io: ZIO[Any, Nothing, Unit] = program.catchAll { e =>
      e match {
        case _: NumberFormatException => ZIO.debug("I need a number")
        case _                        => ZIO.debug("I guess you are not eligible")
      }
    }
      io.exitCode
}
