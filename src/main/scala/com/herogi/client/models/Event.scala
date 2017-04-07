package com.herogi.client.models

/**
  * Created by fatihdonmez on 07/04/17.
  */
case class Event(sessionId: String, eventName: String, scenarioNames: Seq[String], params: Option[Map[String, String]])

trait Response {
  def message: String
}

case class SuccessResponse(message: String) extends Response

case class FailureResponse(code: Int, `type`: String, message: String) extends Response