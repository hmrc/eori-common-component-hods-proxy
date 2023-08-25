/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package util

import com.github.tomakehurst.wiremock.client.WireMock._
import play.api.test.Helpers._
import play.mvc.Http.HeaderNames.CONTENT_TYPE
import play.mvc.Http.MimeTypes.JSON

trait RegisterWithoutIdService {

  val RegWithoutIdServiceContext = "register-without-id"
  val RegWithoutIdServiceUrl     = urlMatching("/" + RegWithoutIdServiceContext)

  def setRegWithoutIdToReturnTheResponse(response: String, status: Int): Unit =
    stubFor(
      post(RegWithoutIdServiceUrl)
        .willReturn(
          aResponse()
            .withStatus(status)
            .withBody(response)
            .withHeader(CONTENT_TYPE, JSON)
        )
    )

}

trait UpdateVerifiedEmailService {

  val UpdateVerifiedEmailContext = "update-verified-email"
  val UpdateVerifiedEmailUrl     = urlMatching("/" + UpdateVerifiedEmailContext)

  def stubUpdateVerifiedEmailResponse(response: String, status: Int): Unit =
    stubFor(
      put(UpdateVerifiedEmailUrl)
        .willReturn(
          aResponse()
            .withStatus(status)
            .withBody(response)
            .withHeader(CONTENT_TYPE, JSON)
        )
    )

}

trait AuthServiceStub {
  val authServiceUrl     = "/auth/authorise"
  val defaultBearerToken = "AUTHORISED_BEARER_TOKEN"

  def stubBearerTokenAuth(bearerToken: String = defaultBearerToken): Unit =
    stubFor(
      post(urlEqualTo(authServiceUrl))
        .withHeader(AUTHORIZATION, equalTo(s"Bearer $bearerToken"))
        .willReturn(
          aResponse()
            .withStatus(OK)
            .withBody("{}")
        )
    )

  def verifyBearerTokenAuthorised(bearerToken: String = defaultBearerToken): Unit =
    verify(
      postRequestedFor(urlEqualTo(authServiceUrl))
        .withHeader(AUTHORIZATION, equalTo(s"Bearer $bearerToken"))
    )

}

trait SubscriptionStatusService {

  val SubscriptionStatusServiceContext = "subscriptionstatus"

  def setSubscriptionStatusToReturnTheResponse(queryParams: String, response: String, status: Int): Unit =
    stubFor(
      get(urlEqualTo("/" + SubscriptionStatusServiceContext + queryParams))
        .willReturn(
          aResponse()
            .withStatus(status)
            .withBody(response)
            .withHeader(CONTENT_TYPE, JSON)
        )
    )

}

trait VatKnownFactsStub {

  val VatKnownFactsContext = "vat/known-facts/control-list/1.0.0"

  def setVatKnownFactsToReturnTheResponse(vrn: String, response: String, status: Int): Unit =
    stubFor(
      get(urlEqualTo("/" + VatKnownFactsContext + "/" + vrn))
        .willReturn(
          aResponse()
            .withStatus(status)
            .withBody(response)
            .withHeader(CONTENT_TYPE, JSON)
        )
    )

}

object ExternalServicesConfig {
  private val basePort = sys.env.getOrElse("WIREMOCK_SERVICE_LOCATOR_PORT", "11111").toInt
  private var p        = basePort

  def port: Int = {
    val current = p
    p = p + 1
    current
  }

  val Host = "localhost"
}
