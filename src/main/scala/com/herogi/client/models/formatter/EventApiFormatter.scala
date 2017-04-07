package com.herogi.client.models.formatter

import com.herogi.client.models.{Event, FailureResponse, SuccessResponse}
import spray.json._
import spray.json.DefaultJsonProtocol

/**
  * Created by fatihdonmez on 07/04/17.
  */
object EventApiFormatter extends DefaultJsonProtocol {

  implicit val EventFormat = jsonFormat4(Event)

  implicit val SuccessResponseFormat = jsonFormat1(SuccessResponse)

  implicit val FailureResponseFormat = jsonFormat3(FailureResponse)
}
