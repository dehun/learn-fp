package learnfp.functor

import org.scalatest.{Matchers, WordSpecLike}
import Disjunction._
import DisjunctionInstance._
import FunctorOps._

class DisjunctionTest extends WordSpecLike with Matchers {
  "either functor" should {
    "works with left" in {
      left[String, Int]("foo") fmap {x => x + "bar"} shouldBe LeftDisjunction[String, Int]("foo")
    }

    "works with right" in {
      right[String, Int](10) fmap {x => x + 20} shouldBe RightDisjunction[String, Int](30)
    }

    "works with disjunction" in {
      val x:Disjunction[String, Int] = RightDisjunction[String, Int](10)
      x fmap {x => x + 20} shouldBe RightDisjunction[String, Int](30)
    }

    "left obeys identity" in {
      val b = left[String, Int]("error")
      b fmap identity shouldBe b
    }

    "right obeys identity" in {
      val a = right[String, Int](10)
      a fmap identity shouldBe a
    }

    "obeys composition" in {
      val f = { x:Int => x + 10 }
      val g = { x:Int => x * 2 }
      val x = right[String, Int](10)

      { x fmap f fmap g } shouldBe { x fmap {f andThen g} }
    }
  }
}
