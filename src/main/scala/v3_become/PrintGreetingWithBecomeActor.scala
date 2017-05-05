package v3_become

import akka.actor.Actor
import protocol.{Goodbye, SayHello, SetGreeting}

class PrintGreetingWithBecomeActor extends Actor {
  override def receive = waitingForGreeting

  def waitingForGreeting: Receive = {
    case SetGreeting(greeting) =>
      println("ready to say hello with " + greeting)
      context.become(withGreeting(greeting))

    case SayHello(_) => println("I don't know how to greet")

    case Goodbye => println("You haven't even said hello")
  }

  def withGreeting(greeting: String): Receive = {
    case SetGreeting(greeting) =>
      println("ready to say hello with " + greeting)
      context.become(withGreeting(greeting))

    case SayHello(who) => println(greeting + " " + who)

    case Goodbye =>
      println("goodbye")
      context.become(waitingForGreeting)
  }
}
