import sbt.Keys._
import sbt._
import uk.gov.hmrc.DefaultBuildSettings.{addTestReportOption, defaultSettings}

import scala.language.postfixOps
name := "eori-common-component-hods-proxy"
majorVersion := 0
PlayKeys.devSettings := Seq("play.server.http.port" -> "6753")
scalaVersion := "2.13.12"
lazy val CdsIntegrationTest = config("it") extend Test
val testConfig = Seq(CdsIntegrationTest, Test)

lazy val testAll = TaskKey[Unit]("test-all")
lazy val allTest = Seq(testAll := (CdsIntegrationTest / test).dependsOn(Test / test).value)
lazy val microservice = (project in file("."))
  .enablePlugins(play.sbt.PlayScala, SbtDistributablesPlugin)
  .disablePlugins(sbt.plugins.JUnitXmlReportPlugin)
  .configs(testConfig: _*)
  .settings(
    libraryDependencies ++= AppDependencies.compile ++ AppDependencies.test,
    unitTestSettings,
    integrationTestSettings,
    allTest,
    scoverageSettings
  )
def onPackageName(rootPackage: String): String => Boolean = {
  testName => testName startsWith rootPackage
}
lazy val unitTestSettings =
  inConfig(Test)(Defaults.testTasks) ++
    Seq(
      Test / testOptions := Seq(Tests.Filter(onPackageName("unit"))),
      Test / unmanagedSourceDirectories := Seq((Test / baseDirectory).value / "test"),
      addTestReportOption(Test, "test-reports")
    )
lazy val integrationTestSettings =
  inConfig(CdsIntegrationTest)(Defaults.testTasks) ++
    Seq(
      CdsIntegrationTest / testOptions := Seq(Tests.Filter(onPackageName("integration"))),
      CdsIntegrationTest / parallelExecution := false,
      addTestReportOption(CdsIntegrationTest, "int-test-reports")
    )
lazy val commonSettings: Seq[Setting[_]] = defaultSettings()
lazy val scoverageSettings: Seq[Setting[_]] = Seq(
  coverageExcludedPackages := List(
    "<empty>"
    , "Reverse.*"
    , ".*(BuildInfo|Routes).*"
    , ".*Logger.*"
  ).mkString(";"),
  coverageMinimumStmtTotal := 94,
  coverageFailOnMinimum := true,
  coverageHighlighting := true,
  Test / parallelExecution := false
)
scalastyleConfig := baseDirectory.value / "project" / "scalastyle-config.xml"

scalacOptions ++= Seq (
  "-Wconf:cat=unused-imports&src=routes/.*:s"
)

