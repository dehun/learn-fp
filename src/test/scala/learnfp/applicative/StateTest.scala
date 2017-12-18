package learnfp.applicative

import org.scalatest.{Matchers, WordSpecLike}
import learnfp.functor.State
import learnfp.functor.State._
import learnfp.functor.StateInstance._
import learnfp.applicative.StateInstance._

class StateTest extends WordSpecLike with Matchers {
  "state applicative" should {
    "work" in {
      def pure(x:Int) =
      { { (x:Int, y:Int, z:Int) => (x, y, z) }.curried `<$>` 10.pure[String] <*> 20.pure[String] <*> 30.pure[String] }.run("asd") shouldBe ("asd", (10, 20, 30))
    }
  }
}
