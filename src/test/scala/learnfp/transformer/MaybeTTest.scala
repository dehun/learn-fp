package learnfp.transformer

import learnfp.functor.{Functor, Id}
import org.scalatest.{Matchers, WordSpecLike}
import learnfp.transformer.MaybeT._
import learnfp.functor.IdInstance._
import learnfp.functor.Maybe.Just
import learnfp.monad.IdInstance._
import learnfp.monad.MonadOps.toMonadOpsPure
import learnfp.functor.FunctorOps._
import learnfp.monad.Monad

class MaybeTTest extends WordSpecLike with Matchers {
  "MaybeT" should {
    def testPure[M[_]]()(implicit f:Functor[M], m:Monad[M]) = {
      type App[A] = MaybeT[A, M];
      {
        for {
          x <- 10.pure[App]
          y <- 20.pure[App]
          z <- 30.pure[App]
        } yield {(x, y, z)}
      }.runMaybeT shouldBe m.pure(Just((10, 20, 30)))
    }

    "work with Id" in {
      testPure[Id]
    }

    import learnfp.functor.Maybe._
    import learnfp.functor.MaybeInstance._
    import learnfp.monad.MaybeInstance._

    "work with Maybe justs" in {
      testPure[Maybe]()
    }

    "work with Maybe nothings" in {
      type App[A] = MaybeT[A, Maybe];
      {
        for {
          x <- 10.pure[App]
          _ <- MaybeT.lift(nothing[Unit]())
          y <- 20.pure[App]
        } yield { (x, y) }
      }.runMaybeT shouldBe nothing()
    }

    import learnfp.functor.ListInstance._
    import learnfp.monad.ListInstance._

    "work with List" in {
      testPure[List]()
    }
    
    import learnfp.functor.Disjunction._
    import learnfp.functor.DisjunctionInstance._
    import learnfp.monad.DisjunctionInstance._

    "work with Disjunction" in {
      testPure[({type E[X] = Disjunction[String, X]})#E]()
    }

    "work with nothingT" in {
      type StringDisjunction[A] = Disjunction[String, A]
      type App[A] = MaybeT[A, StringDisjunction];
      {
        for {
          x <- 10.pure[App]
          _ <- nothingT[Unit, StringDisjunction]
          y <- 20.pure[App]
        } yield {(x, y)}
      }.runMaybeT shouldBe RightDisjunction(nothing())
    }

    import learnfp.functor.State
    import learnfp.functor.State._
    import learnfp.functor.StateInstance.stateInstance
    import learnfp.monad.StateInstance.stateMonadInstance

    "work with State modifications" in {
      type StringState[A] = State[String, A]
      type App[A] = MaybeT[A, StringState];
      {
        for {
          x <- 10.pure[App]
          _ <- MaybeT.lift[Unit, StringState](put("boom"))
          y <- 20.pure[App]
          z <- 30.pure[App]
        } yield {(x, y, z)}
      }.runMaybeT.run("baam") shouldBe ("boom", Just(10, 20, 30))
    }

    "do not modify State after nothingT encountered" in {
      type StringState[A] = State[String, A]
      type App[A] = MaybeT[A, StringState];
      {
        for {
          x <- 10.pure[App]
          _ <- MaybeT.lift[Unit, StringState](put("boom 2"))
          y <- 20.pure[App]
          _ <- nothingT[Unit, StringState]
          _ <- MaybeT.lift[Unit, StringState](put("boom 3"))
          z <- 30.pure[App]
        } yield {(x, y, z)}
      }.runMaybeT.run("baam") shouldBe ("boom 2", nothing())
    }
  }
}
