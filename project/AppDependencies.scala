import play.core.PlayVersion
import sbt._

object AppDependencies {

  val bootstrapVersion = "8.4.0"

  val compile = Seq(
    "uk.gov.hmrc"              %% "bootstrap-backend-play-30"      % bootstrapVersion,
    "uk.gov.hmrc"              %% "internal-auth-client-play-30"   % "1.8.0"
  )

  val test = Seq(
    "org.scalatest"            %% "scalatest"                      % "3.2.15"            % "test,it",
    "org.pegdown"              %  "pegdown"                        % "1.6.0"             % "test,it",
    "org.playframework"        %% "play-test"                      % PlayVersion.current % "test,it",
    "org.scalatestplus.play"   %% "scalatestplus-play"             % "5.1.0"             % "test,it",
    "org.scalatestplus"        %% "mockito-4-6"                    % "3.2.15.0"          % "test,it",
    "org.mockito"              %  "mockito-core"                   % "5.2.0"             % "test,it",
    "com.vladsch.flexmark"     %  "flexmark-all"                   % "0.64.6"            % "test,it",
    "uk.gov.hmrc"              %% "bootstrap-test-play-30"         % bootstrapVersion    % "test,it"
  )
}