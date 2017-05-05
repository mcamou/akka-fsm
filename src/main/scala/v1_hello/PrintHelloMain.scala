package v1_hello

import akka.actor.{ActorSystem, Props}
import protocol.{Goodbye, SayHello}

object PrintHelloMain {
  def main(args: Array[String]): Unit = {
    val sys = ActorSystem("printHello")
    val actor = sys.actorOf(Props(classOf[PrintHelloActor], "Hello ", "Goodbye "))
    actor ! SayHello("foo")
    actor ! Goodbye
  }
}
