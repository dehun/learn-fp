package learnfp.cofunctor

import learnfp.cofunctor.Predicate._
import learnfp.cofunctor.CoFunctorOps._
import org.scalatest.{Matchers, WordSpecLike}

class PredicateTest extends WordSpecLike with Matchers {
  "Predicate" should {
    "work" in {
      { Predicate {x:Int => x % 2 == 0} cmap {x:String => x.toInt} cmap {x:Double => x.floor.toInt.toString } }.getPredicate(10.7) shouldBe true
    }
  }
}
