import akka.actor.{ActorRef, ActorSystem, Props}
import protocol.{Goodbye, SayHello, SetGreeting}
import v2_stateful.PrintGreetingStatefulActor
import v3_become.PrintGreetingWithBecomeActor
import v4_fsm.PrintGreetingFSM
import v5_adv_fsm.PrintGreetingFSMAdv

case object Main {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("v1_hello")

    val actor1 = system.actorOf(Props[PrintGreetingStatefulActor], "stateful")
    test(actor1)

    val actor2 = system.actorOf(Props[PrintGreetingWithBecomeActor], "become")
    test(actor2)

    val actor3 = system.actorOf(Props[PrintGreetingFSM], "fsm")
    test(actor3)

    val actor4 = system.actorOf(Props[PrintGreetingFSMAdv], "fsm_adv")
    test(actor4)

    Thread.sleep(10)
    system.terminate()
  }

  def test(actor: ActorRef): Unit = {
    println("\nTesting " + actor + "\n")
    actor ! SayHello("world")
    actor ! Goodbye
    actor ! SetGreeting("v1_hello")
    actor ! SayHello("world")
    actor ! SetGreeting("hola")
    actor ! SayHello("mundo")
    actor ! Goodbye
    actor ! SayHello("world")
    actor ! "Whatever"
  }
}
