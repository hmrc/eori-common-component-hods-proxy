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

package integration

import java.net.ConnectException
import play.api.test.FakeRequest
import play.api.test.Helpers._
import uk.gov.hmrc.customs.hodsproxy.connectors._
import uk.gov.hmrc.customs.hodsproxy.controllers._
import util.{ExternalServices}

class ControllersSpec extends IntegrationTestSpec with ExternalServices {

  private def fakeRequest(method: String, uri: String) =
    FakeRequest(method, uri).withHeaders("Authorization" -> s"Bearer $defaultBearerToken")

  override protected def beforeEach(): Unit =
    super.beforeEach()

  "Controllers are configured correctly and securely in routes" must {

    "RegisterWithoutIdController is configured with correct url and correct connector" in {

      app.injector.instanceOf[RegisterWithoutIdController].connector shouldBe a[RegisterWithoutIdConnector]

      val result = route(app, fakeRequest("POST", "/register-without-id")).get

      status(result) should be(BAD_REQUEST)
    }

    "RegisterWithIdController is configured with correct url and correct connector" in {

      app.injector.instanceOf[RegisterWithIdController].connector shouldBe a[RegisterWithIdConnector]

      val result = route(app, fakeRequest("POST", "/register-with-id")).get

      status(result) should be(BAD_REQUEST)
    }

    "RegisterWithEoriAndIdController is configured with correct url and correct connector" in {

      app.injector.instanceOf[RegisterWithEoriAndIdController].connector shouldBe a[RegisterWithEoriAndIdConnector]

      val result = route(app, fakeRequest("POST", "/register-with-eori-and-id")).get

      status(result) should be(BAD_REQUEST)
    }

    "SubscriptionController is configured with correct url and correct connector" in {

      app.injector.instanceOf[SubscriptionController].connector shouldBe a[SubscriptionConnector]

      val result = route(app, fakeRequest("POST", "/subscribe")).get

      status(result) should be(BAD_REQUEST)
    }

    // TODO Below tests has ignore flag. After changing this to in all tests failed
    // As I checked there is no dependency on eori-common-component-hods-stubs (customs-hods-stubs)
    "SubscriptionStatusController is configured with correct connector" ignore {

      app.injector.instanceOf[SubscriptionStatusController].connector shouldBe a[SubscriptionStatusConnector]

      intercept[ConnectException] {
        await(route(app, fakeRequest("GET", "/subscription-status")).get)
      }.getMessage contains "subscriptions/subscriptionstatus/1.0.0'"
      //Be mindful that this test fails if customs-hods-stubs is running on your box.
      //TODO We need to re-write this test to make it independent on customs-hods-stubs
    }

    "SubscriptionDisplayController is configured with correct connector" ignore {
      app.injector.instanceOf[SubscriptionDisplayController].connector shouldBe a[SubscriptionDisplayConnector]

      intercept[ConnectException] {
        await(route(app, fakeRequest("GET", "/subscription-display")).get)
      }.getMessage contains "subscriptions/subscriptiondisplay/1.0.0'"
    }

    "VatKnownFactsControlListController is configured with correct connector" ignore {

      app.injector.instanceOf[VatKnownFactsControlListController].connector shouldBe a[
        VatKnownFactsControlListConnector
      ]

      intercept[ConnectException] {
        await(route(app, fakeRequest("GET", "/vat-known-facts-control-list?vrn=12345678")).get)
      }.getMessage contains "subscriptions/subscriptionstatus/1.0.0'"
      //Be mindful that this test fails if customs-hods-stubs is running on your box.
      //TODO We need to re-write this test to make it independent on customs-hods-stubs
    }
  }
}
