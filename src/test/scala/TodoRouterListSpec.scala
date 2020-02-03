import akka.http.scaladsl.model.StatusCodes
import org.scalatest.matchers.should.Matchers
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.WordSpec

class TodoRouterListSpec extends WordSpec with Matchers with ScalatestRouteTest with TodoMocks {
  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._

  private val doneTodo = Todo("1", "Buy milk", "The cat is thirsty", done = true)
  private val pendingTodo = Todo("2", "go home", "miss home", done = false)

  private val todos = Seq(doneTodo, pendingTodo)

  "TodoRouter" should {
    "return all the todos" in {
      val repository = new InMemoryTodoRepository(todos)
      val router = new TodoRouter(repository)

      Get("/todos") ~> router.route ~> check {
        status shouldBe StatusCodes.OK
        val response = responseAs[Seq[Todo]]
        response shouldBe todos
      }
    }

    "return all the done todos" in {
      val repository = new InMemoryTodoRepository(todos)
      val router = new TodoRouter(repository)

      Get("/todos/done") ~> router.route ~> check {
        status shouldBe StatusCodes.OK
        val response = responseAs[Seq[Todo]]
        response shouldBe Seq(doneTodo)
      }
    }

    "return all the pending todos" in {
      val repository = new InMemoryTodoRepository(todos)
      val router = new TodoRouter(repository)

      Get("/todos/pending") ~> router.route ~> check {
        status shouldBe StatusCodes.OK
        val response = responseAs[Seq[Todo]]
        response shouldBe Seq(pendingTodo)
      }
    }

    "handle repository failure in the todos route" in {
      val repository = new FailingRepository
      val router = new TodoRouter(repository)

      Get("/todos") ~> router.route ~> check {
        status shouldBe StatusCodes.InternalServerError
        val resp = responseAs[String]
        resp shouldBe ApiError.generic.message
      }
    }

    "handle repository failure in the todos done route" in {
      val repository = new FailingRepository
      val router = new TodoRouter(repository)

      Get("/todos/done") ~> router.route ~> check {
        status shouldBe ApiError.generic.statusCode
      }
    }

    "handle repository failure in the todos pending route" in {
      val repository = new FailingRepository
      val router = new TodoRouter(repository)

      Get("/todos/pending") ~> router.route ~> check {
        status shouldBe ApiError.generic.statusCode
      }
    }
  }
}
