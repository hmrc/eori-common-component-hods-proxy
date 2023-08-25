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

package uk.gov.hmrc.customs.hodsproxy.metrics

import com.codahale.metrics.Timer.Context
import com.google.inject.Singleton
import com.kenshoo.play.metrics.Metrics
import javax.inject.Inject
import uk.gov.hmrc.customs.hodsproxy.metrics.MetricsEnum.MetricsEnum

@Singleton
class CdsMetrics @Inject() (metrics: Metrics) {

  val timers = Map(
    MetricsEnum.SUBSCRIBE                 -> metrics.defaultRegistry.timer("subscribe-response-timer"),
    MetricsEnum.REGISTER_WITH_ID_MATCH    -> metrics.defaultRegistry.timer("messaging-register-with-id-timer"),
    MetricsEnum.REGISTER_WITHOUT_ID       -> metrics.defaultRegistry.timer("messaging-register-without-id-timer"),
    MetricsEnum.REGISTER_WITH_EORI_AND_ID -> metrics.defaultRegistry.timer("messaging-register-with-eori-timer"),
    MetricsEnum.SUBSCRIPTION_STATUS       -> metrics.defaultRegistry.timer("messaging-subscription-status-timer"),
    MetricsEnum.SUBSCRIPTION_DISPLAY      -> metrics.defaultRegistry.timer("messaging-subscription-display-timer"),
    MetricsEnum.REGISTRATION_DISPLAY      -> metrics.defaultRegistry.timer("messaging-registration-display-timer"),
    MetricsEnum.VAT_KNOWN_FACTS_CONTROL_LIST -> metrics.defaultRegistry.timer(
      "messaging-vat-known-facts-control-list-timer"
    ),
    MetricsEnum.UPDATE_VERIFIED_EMAIL -> metrics.defaultRegistry.timer("messaging-update-verified-email-timer")
  )

  val successCounters = Map(
    MetricsEnum.SUBSCRIBE                 -> metrics.defaultRegistry.counter("subscribe-success-counter"),
    MetricsEnum.REGISTER_WITH_ID_MATCH    -> metrics.defaultRegistry.counter("messaging-register-with-id-success-counter"),
    MetricsEnum.REGISTER_WITHOUT_ID       -> metrics.defaultRegistry.counter("messaging-register-without-id-success-counter"),
    MetricsEnum.REGISTER_WITH_EORI_AND_ID -> metrics.defaultRegistry.counter("messaging-register-with-eori"),
    MetricsEnum.SUBSCRIPTION_STATUS       -> metrics.defaultRegistry.counter("messaging-subscription-status-success-counter"),
    MetricsEnum.SUBSCRIPTION_DISPLAY -> metrics.defaultRegistry.counter(
      "messaging-subscription-display-success-counter"
    ),
    MetricsEnum.REGISTRATION_DISPLAY -> metrics.defaultRegistry.counter(
      "messaging-registration-display-success-counter"
    ),
    MetricsEnum.VAT_KNOWN_FACTS_CONTROL_LIST -> metrics.defaultRegistry.counter(
      "messaging-vat-known-facts-control-list-counter"
    ),
    MetricsEnum.UPDATE_VERIFIED_EMAIL -> metrics.defaultRegistry.counter(
      "messaging-update-verified-email-success-counter"
    )
  )

  val failedCounters = Map(
    MetricsEnum.SUBSCRIBE              -> metrics.defaultRegistry.counter("subscribe-failed-counter"),
    MetricsEnum.REGISTER_WITH_ID_MATCH -> metrics.defaultRegistry.counter("messaging-register-with-id-failed-counter"),
    MetricsEnum.REGISTER_WITHOUT_ID    -> metrics.defaultRegistry.counter("messaging-register-without-id-failed-counter"),
    MetricsEnum.REGISTER_WITH_EORI_AND_ID -> metrics.defaultRegistry.counter(
      "messaging-register-with-eori-failed-counter"
    ),
    MetricsEnum.SUBSCRIPTION_STATUS -> metrics.defaultRegistry.counter("messaging-subscription-status-failed-counter"),
    MetricsEnum.SUBSCRIPTION_DISPLAY -> metrics.defaultRegistry.counter(
      "messaging-subscription-display-failed-counter"
    ),
    MetricsEnum.REGISTRATION_DISPLAY -> metrics.defaultRegistry.counter(
      "messaging-registration-display-failed-counter"
    ),
    MetricsEnum.VAT_KNOWN_FACTS_CONTROL_LIST -> metrics.defaultRegistry.counter(
      "messaging-vat-known-facts-control-list-failed-counter"
    ),
    MetricsEnum.UPDATE_VERIFIED_EMAIL -> metrics.defaultRegistry.counter(
      "messaging-update-verified-email-failed-counter"
    )
  )

  def startTimer(api: MetricsEnum): Context = timers(api).time()

  def incrementSuccessCounter(api: MetricsEnum): Unit = successCounters(api).inc()

  def incrementFailedCounter(api: MetricsEnum): Unit = failedCounters(api).inc()
}
