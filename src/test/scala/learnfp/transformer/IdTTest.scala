package learnfp.transformer

import learnfp.transformer.IdT._
import org.scalatest.{Matchers, WordSpecLike}

import learnfp.functor.Functor
import learnfp.functor.FunctorOps._
import learnfp.monad.Monad
import learnfp.monad.MonadOps.toMonadOpsPure

import learnfp.functor.Id
import learnfp.functor.IdInstance._
import learnfp.monad.IdInstance._

import learnfp.functor.Maybe._
import learnfp.functor.MaybeInstance._
import learnfp.monad.MaybeInstance._

import learnfp.functor.ListInstance._
import learnfp.monad.ListInstance._

import learnfp.functor.Disjunction._
import learnfp.functor.DisjunctionInstance._
import learnfp.monad.DisjunctionInstance._

import learnfp.transformer.MonadTransformer._

class IdTTest extends WordSpecLike with Matchers {
  "IdT" should {

    def testPure[M[_]](implicit functor:Functor[M], monad:Monad[M]) = {
      type App[A] = IdT[A, M];
      {
        for {
          x <- 10.pure[App]
          y <- 20.pure[App]
          z <- 30.pure[App]
        } yield { x + y + z }
      }.runIdT shouldBe (10+20+30).pure[App].runIdT
    }

    def testLift[M[_]](implicit f:Functor[M], m:Monad[M]) = {
      IdT.idtMonadTransInstance[M].lift(m.pure(10)).runIdT shouldBe IdT.idtMonadInstance[M].pure(10).runIdT
    }

    "work with Id" in {
      testPure[Id]
    }

    "lift with Id" in {
      testLift[Id]
    }

    "work with Maybe justs" in {
      testPure[Maybe]
    }

    "work with Maybe nothing" in {
      type App[A] = IdT[A, Maybe];
      {
        for {
          x <- 10.pure[App]
          a <- IdT.lift(nothing[Unit])
          y <- 20.pure[App]
          z <- 30.pure[App]
        } yield { (a, x + y + z) }
      }.runIdT shouldBe nothing[Unit]()
    }


    "lift with Maybe" in {
      testLift[Maybe]
    }

    "work with List" in {
      testPure[List]
    }

    "lift with Lift" in {
      testLift[List]
    }

    "work with Disjunction" in {
      testPure[({type E[X] = Disjunction[String, X]})#E]
    }

    "lift with Disjunction" in {
      testLift[({type E[X] = Disjunction[String, X]})#E]
    }

    import learnfp.functor.State
    import learnfp.functor.State._
    import learnfp.functor.StateInstance.stateInstance
    import learnfp.monad.StateInstance.stateMonadInstance

    "work with State" in {
      type StringState[A] = State[String, A]
      type App[A] = IdT[A, StringState];
      {
        for {
          x <- 10.pure[App]
          y <- "a".pure[App]
          _ <- IdT.lift[Unit, StringState](State.put("baam"));
          z <- 30.pure[App]
        } yield { (x, y, z) }
      }.runIdT.run("boom") shouldBe ("baam", Id(10, "a", 30))
    }
  }
}
