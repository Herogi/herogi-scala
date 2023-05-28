package com.herogi.client

import akka.actor.ActorSystem
import com.herogi.client.models.{Event, FailureResponse}
import com.typesafe.config.ConfigFactory
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatest.{FunSuite, Matchers}


class EventSenderSpec extends FunSuite with Matchers with ScalaFutures {

  val config = ConfigFactory.parseString(
    """
      |akka.ssl-config.ssl.loose.acceptAnyCertificate=true
      |akka.ssl-config.loose.disableHostnameVerification=true
        """.stripMargin)

  implicit val system = ActorSystem("test-system", config)
  implicit val ec = scala.concurrent.ExecutionContext.Implicits.global

  implicit val defaultPatience =
    PatienceConfig(timeout = Span(5, Seconds), interval = Span(50, Millis))

  val eventSender = new HerogiEventSender("appid", "appsecret")

  val event = new Event(None, "e1", List("scenario1"), None)

  test("Send an unauthorized event") {

    val futureResponse = eventSender.sendEvent(event)
    whenReady(futureResponse) { res =>
      res should have (
        'code (401)
      )
    }
  }
}
