package learnfp.functor

import org.scalatest.{Matchers, WordSpecLike}

import Maybe._
import FunctorOps._
import MaybeInstance._

class MaybeTest extends WordSpecLike with Matchers {
  "maybe functor" should {
    "works on Just" in {
      just(1) fmap {x:Int => (x + 2) toString } shouldBe Just("3")
    }
    "works on Nothing" in {
      nothing[Int]() fmap {x:Int => x + 2} shouldBe Nothing[Int]()
    }
    "works on Maybe" in {
      val m:Maybe[Int] = Just(1)
      m fmap {x:Int => x + 2} shouldBe Just[Int](3)
    }
    "obey identity" in {
      just(1) fmap identity shouldBe Just(1)
      nothing[Int]() fmap identity shouldBe Nothing[Int]()
    }
    "obey composition" in {
      val a = {x:Int => x + 2}
      val b = {x:Int => x * 2}
      { just(1) fmap a fmap b } shouldBe { just(1) fmap { a andThen b }}
    }
  }
}
