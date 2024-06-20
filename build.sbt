import sbt.Keys._
import sbt._
import uk.gov.hmrc.DefaultBuildSettings.{addTestReportOption, defaultSettings}
import play.sbt.PlayImport._
import play.sbt.PlayScala

import scala.language.postfixOps

name := "eori-common-component-hods-proxy"
ThisBuild / majorVersion := 0

PlayKeys.devSettings := Seq("play.server.http.port" -> "6753")
ThisBuild / scalaVersion := "2.13.13"

lazy val IntegrationTest = config("it") extend Test

lazy val microservice = (project in file("."))
  .enablePlugins(PlayScala, SbtDistributablesPlugin)
  .disablePlugins(sbt.plugins.JUnitXmlReportPlugin)
  .configs(IntegrationTest)
  .settings(
    commonSettings,
    libraryDependencies ++= (AppDependencies.compile ++ AppDependencies.test),
    unitTestSettings,
    integrationTestSettings,
    scoverageSettings,
    scalastyleConfig := baseDirectory.value / "project" / "scalastyle-config.xml",
    scalacOptions ++= Seq(
      "-Wconf:cat=unused-imports&src=routes/.*:s",
      "-Wconf:cat=unused-imports&src=html/.*:s"
    )
  )

def onPackageName(rootPackage: String): String => Boolean = {
  testName => testName startsWith rootPackage
}

lazy val unitTestSettings = inConfig(Test)(Defaults.testTasks) ++ Seq(
  Test / testOptions := Seq(Tests.Filter(onPackageName("unit"))),
  Test / unmanagedSourceDirectories := Seq((Test / baseDirectory).value / "test"),
  addTestReportOption(Test, "test-reports")
)

lazy val integrationTestSettings = inConfig(IntegrationTest)(Defaults.testTasks) ++ Seq(
  IntegrationTest / parallelExecution := false,
  addTestReportOption(IntegrationTest, "int-test-reports"),
  IntegrationTest / unmanagedSourceDirectories := Seq(baseDirectory.value / "it")
)

lazy val commonSettings: Seq[Setting[_]] = defaultSettings()

lazy val scoverageSettings: Seq[Setting[_]] = Seq(
  coverageExcludedPackages := List(
    "<empty>",
    "Reverse.*",
    ".*(BuildInfo|Routes).*",
    ".*Logger.*"
  ).mkString(";"),
  coverageMinimumStmtTotal := 94,
  coverageFailOnMinimum := true,
  coverageHighlighting := true,
  Test / parallelExecution := false
)
