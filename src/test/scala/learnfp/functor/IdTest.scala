package learnfp.functor

import org.scalatest.{Matchers, WordSpecLike}

class IdTest extends WordSpecLike with Matchers {
  import IdInstance._
  import FunctorOps._

  "id" should {
    "work on simple functions" in {
      Id(1) fmap {x:Int => x + 1} shouldBe Id(2)
      Id("one, ") fmap {s => s + "two"} shouldBe Id("one, two")
    }

    "obey identity" in {
      Id(1) fmap identity shouldBe Id(1)
    }
    
    "obey composition" in {
      val f = {x:Int => x + 1}
      val g = {x:Int => x * 2}

      { Id(2) fmap f fmap g } shouldBe { Id(2) fmap (f andThen g) }
    }
  }
}
