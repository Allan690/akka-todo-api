import akka.actor.ActorSystem

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits._
import scala.util.{Failure, Success}

object Main  extends App {
  val host = "0.0.0.0"
  val port = 9000

  implicit val system: ActorSystem = ActorSystem(name="todoApi")

  val todoRepository = new InMemoryTodoRepository(Seq(
    Todo("1", "Visit Mara", "on my bucket list", false),
    Todo("2", "Buy lunch", "i'm hungy", true),
    Todo("3", "Visit parents", "miss home", false)
  ))
  val router = new TodoRouter(todoRepository)
  val server = new Server(router, host, port)
  val binding = server.bind;
  binding.onComplete {
    case Success(_) => println("success")
    case Failure(e) => println(s"Failed with error: ${e.getMessage}")
  }
  import scala.concurrent.duration._
  Await.result(binding, 3.seconds)
}
