package learnfp.monad

import org.scalatest.{Matchers, WordSpecLike}

import learnfp.functor.FunctorOps._
import learnfp.monad.MonadOps._

import learnfp.monad.MaybeInstance._
import learnfp.functor.MaybeInstance._
import learnfp.functor.Maybe._

class MaybeTest extends WordSpecLike with Matchers {
  "maybe monad" should {
    "work when everything is just" in {
      {
        for {
          x <- 10.pure
          y <- just(20)
        } yield { x + y }
      } shouldBe just(30)
    }

    "work when nothing" in {
      {
        for {
          x <- 10.pure
          y <- nothing[Int]()
        } yield { x + y }
      } shouldBe nothing()
    }
  }
}
