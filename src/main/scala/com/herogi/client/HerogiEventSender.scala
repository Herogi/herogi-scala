package com.herogi.client

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.BasicHttpCredentials
import akka.http.scaladsl.unmarshalling.{Unmarshal, Unmarshaller}
import akka.stream.ActorMaterializer
import com.herogi.client.models._
import com.herogi.client.models.formatter.EventApiFormatter._

import scala.concurrent.{ExecutionContext, Future}


class HerogiEventSender(appid: String,
                        appSecret: String,
                        api: String = "https://stream.herogi.com",
                        enabled: Boolean = true)
                       (implicit system: ActorSystem, ec: ExecutionContext) {

  private val EventApi = api + "/v2/event"

  val authorization = headers.Authorization(BasicHttpCredentials(appid, appSecret))

  implicit val materializer = ActorMaterializer()

  def sendEvent(event: Event): Future[Response] = enabled match {
    case true =>
      for {
        entity <- Marshal(event).to[RequestEntity]
        response <- Http().singleRequest(HttpRequest(uri = EventApi , method = HttpMethods.POST, headers = List(authorization), entity = entity))
        obj <- deserialize(response)
      } yield obj
    case false => Future.successful(SuccessResponse("success - (debug mode)"))
  }

  private def deserialize[T](res: HttpResponse)(implicit um: Unmarshaller[ResponseEntity, T]): Future[Response] = {
    res.status match {
      case StatusCodes.OK => Unmarshal(res.entity).to[SuccessResponse]
      case _ => Unmarshal(res.entity).to[FailureResponse]
    }
  }

}
