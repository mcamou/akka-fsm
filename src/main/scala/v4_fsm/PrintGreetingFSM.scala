package v4_fsm

import akka.actor.{Actor, FSM}
import protocol.{Goodbye, SayHello, SetGreeting}
import PrintGreetingFSM._

class PrintGreetingFSM extends Actor with FSM[State, Data] {

  startWith(WaitingForGreeting, NoGreeting)

  when(WaitingForGreeting) {
    case Event(SetGreeting(greeting), _) =>
      println("ready to say hello with " + greeting)
      goto(WithGreeting) using Greeting(greeting)

    case Event(SayHello(_), _) =>
      println("I don't know how to greet")
      stay

    case Event(Goodbye, _) =>
      println("You haven't even said hello")
      stay
  }

  when(WithGreeting) {
    case Event(SetGreeting(greeting), _) =>
      println("ready to say hello with " + greeting)
      stay using Greeting(greeting)

    case Event(SayHello(who), Greeting(greeting)) =>
      println(greeting + " " + who)
      stay

    case Event(Goodbye, _) =>
      println("goodbye")
      goto(WaitingForGreeting) using NoGreeting
  }
}

object PrintGreetingFSM {
  sealed trait State
  case object WaitingForGreeting extends State
  case object WithGreeting extends State

  sealed trait Data
  case object NoGreeting extends Data
  case class Greeting(greeting: String) extends Data
}

