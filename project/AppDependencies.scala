import play.core.PlayVersion
import play.sbt.PlayImport._
import sbt.Keys.libraryDependencies
import sbt._

object AppDependencies {

  val bootstrapVersion = "7.22.0"

  val compile = Seq(
    "uk.gov.hmrc"              %% "bootstrap-backend-play-28"      % bootstrapVersion,
    "uk.gov.hmrc"              %% "internal-auth-client-play-28"   % "1.6.0"
  )

  val test = Seq(
    "org.scalatest"            %% "scalatest"                      % "3.2.15"            % "test,it",
    "org.pegdown"              %  "pegdown"                        % "1.6.0"             % "test,it",
    "com.typesafe.play"        %% "play-test"                      % PlayVersion.current % "test,it",
    "org.scalatestplus.play"   %% "scalatestplus-play"             % "5.1.0"             % "test,it",
    "org.scalatestplus"        %% "mockito-4-6"                    % "3.2.15.0"          % "test,it",
    "com.github.tomakehurst"   %  "wiremock-jre8"                  % "2.27.2"            % "test,it",
    "org.mockito"              %  "mockito-core"                   % "5.2.0"             % "test,it",
    "com.vladsch.flexmark"     %  "flexmark-all"                   % "0.64.6"            % "test,it",
    "uk.gov.hmrc"              %% "bootstrap-test-play-28"         % bootstrapVersion    % "test,it"
  )
}