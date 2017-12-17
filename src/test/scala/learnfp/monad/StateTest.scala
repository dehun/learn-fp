package learnfp.monad
import org.scalatest.{Matchers, WordSpecLike}

import learnfp.monad.MonadOps.toMonadOpsPure
import learnfp.monad.StateInstance._
import learnfp.functor.StateInstance._
import learnfp.functor.State._
import learnfp.functor.State

class StateTest extends WordSpecLike with Matchers {
  type StringState[A] = State[String, A]
  "state monad" should {
    "work" in {
      {
        for {
          x <- 10.pure[StringState]
          os <- State.get[String]
          y <- 20.pure[StringState]
          _ <- State.put(os + " boom")
          z <- 30.pure[StringState]
          _ <- State.get[String] >>= { ns => State.put(ns + " baam") }
        } yield {x + y + z}
      }.run("boom") shouldBe ("boom boom baam", 10 + 20 + 30)
    }
  }
}
