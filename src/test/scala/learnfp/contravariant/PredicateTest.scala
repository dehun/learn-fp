package learnfp.contravariant

import learnfp.contravariant.Predicate._
import learnfp.contravariant.ContravariantFunctorOps._
import org.scalatest.{Matchers, WordSpecLike}

class PredicateTest extends WordSpecLike with Matchers {
  "Predicate" should {
    "work" in {
      val predicate = { Predicate {x:Int => x % 2 == 0} cmap
        {x:String => x.toInt} cmap
        {x:Double => x.floor.toInt.toString } }
      predicate.getPredicate(10.7) shouldBe true
      predicate.getPredicate(11.7) shouldBe false
    }

    "obey identity" in {
      val predicate = { Predicate {x:Int => x % 2 == 0} cmap identity[Int]}
      predicate.getPredicate(8) shouldBe true
      predicate.getPredicate(7) shouldBe false
    }

    "obey composition" in {
      def f(x:Int) = x * 3
      def g(x:Int) = x * 5

      val lpredicate = { Predicate {x:Int => x % 2 == 0} cmap f cmap g}
      val rpredicate = {Predicate {x:Int => x % 2 == 0} cmap (f _ andThen g)}
      lpredicate.getPredicate(8) shouldBe rpredicate.getPredicate(8)
      lpredicate.getPredicate(7) shouldBe rpredicate.getPredicate(7)
    }
  }
}
