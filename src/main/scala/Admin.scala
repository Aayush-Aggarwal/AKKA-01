import akka.actor.ActorSystem
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration.DurationInt
import scala.concurrent.ExecutionContext.Implicits.global

object  Admin extends App {

  val customer = Customer("Ayush", "E-132, Sector-41, Noida", 1244445555616161L, 7838467221L)

  val actorSystem = ActorSystem("SmartphonePurchaseSystem")

  val purchaseActorMaster = actorSystem.actorOf(PurchaseActorMaster.props)

  val validationActor = actorSystem.actorOf(ValidationActor.props(purchaseActorMaster))

  val purchaseRequestHandler = actorSystem.actorOf(PurchaseRequestHandler.props(validationActor))

  implicit val timeout = Timeout(100 seconds)

  val a = purchaseRequestHandler ? customer

  a.foreach(println(_))
}