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

    "work with Maybe" in {
      testPure[Maybe]()
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
  }
}
