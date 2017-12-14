package learnfp.traversable

import org.scalatest.{Matchers, WordSpecLike}

class TraversableTest extends WordSpecLike with Matchers {
  import learnfp.functor.Maybe._
  import learnfp.functor.MaybeInstance._
  import learnfp.applicative.MaybeInstance._
  import learnfp.traversable.TraversableOps._

  "traversable" should {
    "sequence maybes" in {
      { List(just(5), just(10), just(3)) sequence } shouldBe just(List(5, 10, 3))
    }

    "traverse maybes" in {
      { List(just(5), just(10), just(3)) traverse {_ + 1} } shouldBe just(List(6, 11, 4))
    }
  }
}
