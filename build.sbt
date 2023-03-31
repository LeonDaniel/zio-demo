val zioVersion            = "2.0.10"
val zioJsonVersion        = "0.4.2"
val zioConfigVersion      = "3.0.2"
val zioLoggingVersion     = "2.1.11"
val logbackClassicVersion = "1.4.6"
val postgresqlVersion     = "42.5.4"
val testContainersVersion = "0.40.11"
val zioMockVersion        = "1.0.0-RC8"
val zioHttpVersion        = "0.0.5"
val quillVersion          = "4.6.0"

lazy val root = (project in file("."))
  .settings(
    inThisBuild(
      List(
        organization := "com.barbecode",
        scalaVersion := "3.2.1",
      )
    ),
    name           := "zio-demo",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio"         % zioVersion,
      "dev.zio" %% "zio-macros"  % zioVersion,
      "dev.zio" %% "zio-http"    % zioHttpVersion,

      // test
      "dev.zio"      %% "zio-test"                        % zioVersion            % Test,
      "dev.zio"      %% "zio-test-sbt"                    % zioVersion            % Test,
      "dev.zio"      %% "zio-test-junit"                  % zioVersion            % Test,
      "dev.zio"      %% "zio-mock"                        % zioMockVersion        % Test,
      "com.dimafeng" %% "testcontainers-scala-postgresql" % testContainersVersion % Test,
      "dev.zio"      %% "zio-test-magnolia"               % zioVersion            % Test,
    ),
    testFrameworks := Seq(new TestFramework("zio.test.sbt.ZTestFramework")),
  )
  .enablePlugins(JavaAppPackaging)
