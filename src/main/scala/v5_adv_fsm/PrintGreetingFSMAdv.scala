package v5_adv_fsm

import PrintGreetingFSMAdv._
import akka.actor.{Actor, FSM}
import protocol.{Goodbye, SayHello, SetGreeting}
import scala.concurrent.duration._

class PrintGreetingFSMAdv extends Actor with FSM[State, Data] {

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

  when(WithGreeting, stateTimeout = 2.seconds) {
    case Event(SetGreeting(greeting), _) =>
      println("ready to say hello with " + greeting)
      stay using Greeting(greeting)

    case Event(SayHello(who), Greeting(greeting)) =>
      println(greeting + " " + who)
      stay

    case Event(Goodbye, _) =>
      println("goodbye")
      goto(WaitingForGreeting) using NoGreeting

    case Event(StateTimeout, _) =>
      println("nobody wants to talk to me :(")
      stay
  }

  onTransition {
    case x -> y =>
      println("Transitioning from " + x + " to " + y + " with state " + stateData)
  }
  whenUnhandled {
    case Event(ev, st) =>
      println("I don't know what to do with " + ev + " in state " + st)
      goto(WaitingForGreeting) using NoGreeting
  }
}

object PrintGreetingFSMAdv {
  sealed trait State
  case object WaitingForGreeting extends State
  case object WithGreeting extends State

  sealed trait Data
  case object NoGreeting extends Data
  case class Greeting(greeting: String) extends Data
}

