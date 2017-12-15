package learnfp.monad

import learnfp.functor.FunctorOps._
import learnfp.monad.MonadOps._
import learnfp.monad.ListInstance._
import learnfp.functor.MaybeInstance._
import learnfp.functor.Maybe._
import org.scalatest.{Matchers, WordSpecLike}

class ListTest extends WordSpecLike with Matchers {
  "list monad" should {
    "works" in {
      { for {
        x <- List(1, 2, 3)
        y <- List(4, 5) } yield (x, y) } shouldBe List((1, 4), (1, 5), (2, 4), (2, 5), (3, 4), (3, 5))
    }

    "flattens" in {
      { List(List(1, 2, 3), List(4, 5, 6), List(7, 8, 9)) >>= identity } shouldBe (1 to 9).toList
    }
  }
}
