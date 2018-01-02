package learnfp.transformer

import org.scalatest.{Matchers, WordSpecLike}

import learnfp.monad.MonadOps.toMonadOpsPure

import learnfp.transformer.WriterT._
import learnfp.transformer.StateT._
import learnfp.functor.Maybe._
import learnfp.functor.MaybeInstance._
import learnfp.monad.MaybeInstance._

import learnfp.monoid.ListMonoid._

class TransformerStackTest extends WordSpecLike with Matchers {
  "Maybe/Writer/State stack" should {
    type OuterT[A] = WriterT[A, Maybe, List[String]]
    type App[A] = StateT[String, OuterT, A];

    "work" in {
      {
        for {
          x <- 10.pure[App]
          // TODO: tell "een"
          // TODO: put state "one"
          y <- 20.pure[App]
          // TODO: tell "twee"
          // TODO: put state "two"
          z <- 30.pure[App]
          // TODO: tell "drie"
          // TODO: put state "three"
        } yield x
      }.runStateT("null").runWriterT() match {
        case Just(v) => v shouldBe (List("een", "twee", "drie"), ("three", 10))
        case Nothing() => throw new AssertionError("got nothing instead of just")
      }
    }
  }
}
