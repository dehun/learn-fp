package learnfp.functor

import org.scalatest.{Matchers, WordSpecLike}
import learnfp.functor.StateInstance._

class StateTest extends WordSpecLike with Matchers {
  "state functor" should {
    "put works" in {
      State.put(20).run(10) shouldBe (20, ())
    }

    "get works " in {
      State.get.eval(10) shouldBe 10
    }

    "fmap works" in {
      { State[String, Int]({s => ("extended " + s, 10)}) fmap {x:Int => x + 20} }.run("state") shouldBe ("extended state", 30)
    }

    "obey identity" in {
      { State[String, Int]({s => (s, 10)}) fmap identity }.run("state") shouldBe ("state", 10)
    }

    "obey composition" in {
      val f = {x:Int => x + 20}
      val g = {x:Int => x * 2}
      { { State[String, Int]({s => (s, 10)}) fmap f fmap g}.run("state") } shouldBe { { State[String, Int]({s => (s, 10)}) fmap { f andThen g } }.run("state") }
    }
  }
}
