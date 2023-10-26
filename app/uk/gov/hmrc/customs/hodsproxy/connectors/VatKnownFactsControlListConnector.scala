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

package uk.gov.hmrc.customs.hodsproxy.connectors

import play.api.Logger

import javax.inject.{Inject, Singleton}
import play.api.http.Status
import uk.gov.hmrc.customs.hodsproxy.metrics.CdsMetrics
import uk.gov.hmrc.customs.hodsproxy.metrics.MetricsEnum.{MetricsEnum, VAT_KNOWN_FACTS_CONTROL_LIST}
import uk.gov.hmrc.http.{HeaderCarrier, HttpResponse}
import uk.gov.hmrc.play.bootstrap.config.ServicesConfig
import uk.gov.hmrc.http.HttpClient

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class VatKnownFactsControlListConnector @Inject() (
  http: HttpClient,
  config: ServicesConfig,
  metrics: CdsMetrics,
  headerGenerator: HeaderGenerator
)(implicit ec: ExecutionContext)
    extends ProxyConnector(http, config, metrics, headerGenerator) {

  private val logger = Logger(this.getClass)

  def get(vrn: String): Future[HttpResponse] = {

    val url = s"${baseUrl(serviceName)}/$vrn"

    implicit val hc = HeaderCarrier(extraHeaders = generateHeaders)

    logger.info(s"[$serviceName][Connector] GET url: $url")

    makeDesRequest(http.GET(url))
  }

  private def makeDesRequest(httpRequest: => Future[HttpResponse]): Future[HttpResponse] = {

    val timerContext = metrics.startTimer(metricsId)
    httpRequest map { response =>
      timerContext.stop()

      logger.info(s"[$serviceName][Connector] - status: ${response.status}")

      response.status match {
        case Status.OK =>
          metrics.incrementSuccessCounter(metricsId)
          response
        case status if Status.isServerError(status) =>
          logger.error(s"[$serviceName][Connector] - status: ${response.status}")
          metrics.incrementFailedCounter(metricsId)
          response
        case _ =>
          logger.warn(s"[$serviceName][Connector] - status: ${response.status}")
          metrics.incrementFailedCounter(metricsId)
          response
      }
    }
  }

  override def generateHeaders: Seq[(String, String)] = {
    val desEnv = config.getString(s"microservice.services.$serviceName.des-environment")
    headerGenerator.headersForDESGet(bearerToken, desEnv)
  }

  override val serviceName: String    = "vat-known-facts-control-list"
  override val metricsId: MetricsEnum = VAT_KNOWN_FACTS_CONTROL_LIST
}
