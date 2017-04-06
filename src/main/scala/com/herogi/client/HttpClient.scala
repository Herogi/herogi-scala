package com.herogi.client

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import spray.json._

import scala.concurrent.{ExecutionContext, Future}


class HttpClient(implicit val system: ActorSystem, val ex: ExecutionContext) {

  implicit val materializer = ActorMaterializer()

  def postRequest[REQ, RSP](url: String, req: REQ)(implicit reqFormat: RootJsonFormat[REQ], rspFormat: RootJsonFormat[RSP]): Future[RSP] = {

    for {
      entity <- Marshal(req).to[RequestEntity]
      response <- Http().singleRequest(HttpRequest(uri = url, method = HttpMethods.POST, entity = entity))
      obj <- Unmarshal(response.entity).to[RSP]
    } yield obj
  }

}
