import akka.actor.{Actor, ActorLogging, ActorRef, Props}

class ValidationActor(purchaseActorMaster: ActorRef) extends Actor with ActorLogging {

  var availability = 1000

  override def receive: Receive = {

    case customer: Customer => if (availability > 0) {
      log.info("Phone is available. Forwarding request to PurchaseActorMaster.")
      availability -= 1
      log.info(s"Item count is $availability")
      purchaseActorMaster.forward(customer)
    }
    else {
      log.info("Phones are out of stock. Sending out of stock message to customer.")
      sender() ! "Out of Stock!"
    }
  }

}

object ValidationActor {

  def props(purchaseActorMaster: ActorRef): Props = Props(classOf[ValidationActor], purchaseActorMaster)

}