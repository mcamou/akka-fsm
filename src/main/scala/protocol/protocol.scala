package protocol

case class SetGreeting(greeting: String)
case class SayHello(who: String)
case object Goodbye
