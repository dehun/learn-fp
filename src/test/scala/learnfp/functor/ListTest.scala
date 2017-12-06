package learnfp.functor

import org.scalatest.{Matchers, WordSpecLike}
import FunctorOps._
import ListInstance._

class ListTest extends WordSpecLike with Matchers {
  "list functor" should {
    "work on simple functions" in {
      List(1) fmap {x:Int => x + 1} shouldBe List(2)
      List(1, 2, 3) fmap {x:Int => x + 1} shouldBe List(2, 3, 4)
    }
    "obey identity" in {
      List(1) fmap identity shouldBe List(1)
    }
    "obey composition" in {
      val f = {x:Int => x + 1}
      val g = {x:Int => x.toString + "a"}
      { List(1) fmap f fmap g } shouldBe { List(1) fmap {f andThen g } }
    }
  }
}
