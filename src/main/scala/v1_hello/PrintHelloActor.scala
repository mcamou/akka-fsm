package v1_hello

import akka.actor.{Actor, Props}
import protocol.{Goodbye, SayHello}

class PrintHelloActor(helloMessage: String, goodbyeMessage: String) extends Actor {
  var user: Option[String] = None

  override def receive: Receive = {

    case SayHello(who) =>
      user = Some(who)
      println(helloMessage + user.get)

    case Goodbye =>
      println(goodbyeMessage + user.get)
      user = None
  }
}

object PrintHelloActor {
  def props(helloMessage: String, g: String) = Props(classOf[PrintHelloActor], helloMessage, g)
}