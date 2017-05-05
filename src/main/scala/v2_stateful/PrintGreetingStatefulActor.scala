package v2_stateful

import akka.actor.Actor
import protocol.{Goodbye, SayHello, SetGreeting}

class PrintGreetingStatefulActor extends Actor {
  var currentGreeting: Option[String] = None

  override def receive: Receive = {
    case SetGreeting(greeting) =>
      println("ready to say hello with " + greeting)
      currentGreeting = Some(greeting)

    case SayHello(who) =>
      currentGreeting match {
        case Some(g) => println(currentGreeting + " " + who)
        case None => println("I don't know how to greet")
      }

    case Goodbye =>
      currentGreeting match {
        case Some(g) =>
          println("goodbye")
          currentGreeting = None

        case None => println("You haven't even said hello")
      }
  }
}
