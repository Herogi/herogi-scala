# herogi-scala
Herogi scala helper library

## Your First Event

Following is minimum needed code send an event to Herogi, you can find the full example [here](https://github.com/Herogi/herogi-scala/blob/master/src/test/scala/com/herogi/client/EventSenderSpec.scala).

```scala
  implicit val system = ActorSystem()
  implicit val ec = scala.concurrent.ExecutionContext.Implicits.global

  val eventSender = new HerogiEventSender("appid", "appsecret")

  val event = new Event("s1", "e1", List("scenario1"), Some(Map("param1" -> "value1")))

  eventSender.sendEvent(event) map { res =>
    println(res)
  }

```

### Sending to All Scenarios
To send all scenarios available, you can skip to scenario specifying during event creation:

```scala
val event = new Event("s1", "e1", List.empty, Some(Map("param1" -> "value1")))

//or better

val event = new Event(sessionId = "s1", eventName = "e1", params = Some(Map("param1" -> "value1")))

```
