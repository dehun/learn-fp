package learnfp.monad

import org.scalatest.{Matchers, WordSpecLike}

import learnfp.monad.MonadOps.toMonadOpsPure
import learnfp.monad.WriterInstance._
import learnfp.functor.WriterInstance._
import learnfp.functor.Writer
import learnfp.functor.Writer._
import learnfp.monoid.ListMonoid._


class WriterTest extends WordSpecLike with Matchers {
  "writer monad" should {
    "work" in {
      type WriterString[A] = Writer[List[String], A];
      {
        for {
          x <- 1.pure[WriterString]
          _ <- tell(List("een"))
          y <- 2.pure[WriterString]
          _ <- tell(List("twee"))
          z <- 3.pure[WriterString]
          _ <- tell(List("drie"))
      } yield (x, y, z) }.run() shouldBe (List("een", "twee", "drie"), (1, 2, 3))
    }
  }
}

