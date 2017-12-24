package learnfp.transformer

import org.scalatest.{WordSpecLike, Matchers}

//import learnfp.functor.FunctorOps.
import learnfp.monad.MonadOps.toMonadOpsPure
import learnfp.transformer.MonadTransformer._
import learnfp.transformer.StateT._

class StateTTest extends WordSpecLike with Matchers {
  "StateT" should {

    import learnfp.functor.Id
    import learnfp.functor.IdInstance._
    import learnfp.monad.IdInstance._

    "work with Id" in {
      type App[A] = StateT[String, Id, A];
      {
        for {
          x <- 10.pure[App]
          _ <- putT[String, Id]("een")
          y <- 20.pure[App]
          _ <- putT[String, Id]("twee")
          z <- 30.pure[App]
          _ <- putT[String, Id]("drie")
        } yield { (x, y, z) }
      }.runStateT("null") shouldBe Id(("drie", (10, 20, 30)))
    }

    import learnfp.functor.Maybe._
    import learnfp.functor.MaybeInstance._
    import learnfp.monad.MaybeInstance._

    "work with Maybe just" in {
      type App[A] = StateT[String, Maybe, A];
      {
        for {
          x <- 10.pure[App]
          _ <- putT[String, Maybe]("een")
          y <- 20.pure[App]
          _ <- putT[String, Maybe]("twee")
          z <- 30.pure[App]
          _ <- putT[String, Maybe]("drie")
        } yield { (x, y, z) }
      }.runStateT("null") shouldBe Just(("drie", (10, 20, 30)))
    }

    "work with Maybe nothing" in {
      type App[A] = StateT[String, Maybe, A];
      {
        for {
          x <- 10.pure[App]
          _ <- putT[String, Maybe]("een")
          y <- 20.pure[App]
          _ <- putT[String, Maybe]("twee")
          z <- 30.pure[App]
          _ <- StateT.lift[String, Maybe, Unit](nothing[Unit]())
          _ <- putT[String, Maybe]("drie")
        } yield { (x, y, z) }
      }.runStateT("null") shouldBe nothing[Unit]()
    }
  }
}
