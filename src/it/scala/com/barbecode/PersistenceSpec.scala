package com.barbecode

import io.github.scottweaver.zio.aspect.DbMigrationAspect
import io.github.scottweaver.zio.testcontainers.mysql.ZMySQLContainer
import zio.*
import zio.test.*

import javax.sql.DataSource

object PersistenceSpec extends ZIOSpecDefault {
  override def spec: Spec[TestEnvironment & Scope, Any] = (suite("Integration tests with MySQL container")(
    test("Read the datasource") {
      for {
        ds <- ZIO.service[DataSource]
        url = ds.getConnection.getMetaData.getURL
        _  <- zio.Console.printLine(url)
      } yield assertTrue(url.contains("jdbc:mysql://localhost"))
//      assertCompletes
    }
  ) @@ DbMigrationAspect.migrate()() @@ TestAspect.sequential)
    .provideShared(ZMySQLContainer.Settings.default, ZMySQLContainer.live)
}
