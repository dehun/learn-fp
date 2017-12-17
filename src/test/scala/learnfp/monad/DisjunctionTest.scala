package learnfp.monad

import org.scalatest.{Matchers, WordSpecLike}

import learnfp.monad.MonadOps.toMonadOpsPure
import learnfp.monad.DisjunctionInstance._
import learnfp.functor.DisjunctionInstance._
import learnfp.functor.Disjunction._

class DisjunctionTest extends WordSpecLike with Matchers {
  type OrError[A] = Disjunction[String, A]

  "disjunction monad" should {
    "work with rights" in {
      {
        for {
          x <- "a".pure[OrError]
          y <- "b".pure[OrError]
          z <- "c".pure[OrError]
        } yield {x + y + z}
      } shouldBe "abc".pure[OrError]
    }

    "work with lefts" in {
      {
        for {
          x <- 10.pure[OrError]
          y <- left[String, Int]("boom")
          z <- 20.pure[OrError]
        } yield {x + y + z}
      } shouldBe left[String, Int]("boom")
    }
  }
}
