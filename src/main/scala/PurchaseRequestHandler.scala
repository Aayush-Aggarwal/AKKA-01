import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}


class PurchaseRequestHandler(validationActor: ActorRef) extends Actor with ActorLogging {

  override def receive: Receive = {

    case customer: Customer => log.info("Customer information received in PurchaseRequestHandler." +
      " & Forwarding to ValidationActor")
      validationActor.forward(customer)

    case _ => log.info("Wrong customer information.")
      sender() ! "You can only buy Samsung Galaxy S8"

  }

}

object PurchaseRequestHandler {

  def props(validationActor: ActorRef): Props = Props(classOf[PurchaseRequestHandler], validationActor)
}

