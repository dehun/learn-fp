package learnfp.transformer

import org.scalatest.{Matchers, WordSpecLike}
import learnfp.functor.FunctorOps._
import learnfp.functor.Writer
import learnfp.monad.MonadOps.toMonadOpsPure
import learnfp.monoid.ListMonoid._
import learnfp.transformer.WriterT._

class WriterTTest extends WordSpecLike with Matchers {
  "WriterT" should {
    import learnfp.functor.Id
    import learnfp.functor.IdInstance._
    import learnfp.monad.IdInstance._

    "work with Id" in {
      type App[A] = WriterT[A, Id, List[String]];
      {
        for {
          x <- 10.pure[App]
          _ <- WriterT.tell(List("1"))
          y <- 20.pure[App]
          _ <- WriterT.tell(List("2"))
          z <- 30.pure[App]
        } yield {(x, y, z)}
      }.runWriterT().value shouldBe (List("1", "2"), (10, 20, 30))
    }

    import learnfp.functor.Maybe._
    import learnfp.functor.MaybeInstance._
    import learnfp.monad.MaybeInstance._

    "work with maybe just" in {
      type App[A] = WriterT[A, Maybe, List[String]];
      {
        for {
          x <- 10.pure[App]
          _ <- WriterT.tell[Maybe, List[String]](List("1"))
          y <- 20.pure[App]
          _ <- WriterT.tell[Maybe, List[String]](List("2"))
          z <- 30.pure[App]
        } yield {(x, y, z)}
      }.runWriterT().asInstanceOf[Just[(List[String], (Int, Int, Int))]].value shouldBe (List("1", "2"), (10, 20, 30))
    }

    "work with maybe nothing" in {
      type App[A] = WriterT[A, Maybe, List[String]];
      {
        for {
          x <- 10.pure[App]
          _ <- WriterT.tell[Maybe, List[String]](List("1"))
          y <- 20.pure[App]
          _ <- WriterT.tell[Maybe, List[String]](List("2"))
          _ <- WriterT.lift[Unit, Maybe, List[String]](nothing[Unit]())
          z <- 30.pure[App]
        } yield { (x, y, z) }
      }.runWriterT().asInstanceOf[Nothing[Unit]] shouldBe nothing[Unit]()
    }

    import learnfp.functor.State
    import learnfp.functor.State._
    import learnfp.functor.StateInstance._
    import learnfp.monad.StateInstance._

    "work with State" in {
      type StringState[A] = State[String, A]
      type App[A] = WriterT[A, StringState, List[String]];
      val r = {
        for {
          x <- 10.pure[App]
          _ <- WriterT.lift[Unit, StringState, List[String]](State.put("een"))
          _ <- WriterT.tell[StringState, List[String]](List("one"))
          y <- 20.pure[App]
          _ <- WriterT.lift[Unit, StringState, List[String]](State.put("twee"))
          _ <- WriterT.tell[StringState, List[String]](List("two"))
          z <- 30.pure[App]
          _ <- WriterT.lift[Unit, StringState, List[String]](State.put("drie"))
          _ <- WriterT.tell[StringState, List[String]](List("three"))
        } yield { (x, y, z) }
      }.runWriterT().run("null")
      r._1 shouldBe "drie"
      r._2 shouldBe (List("one", "two", "three"), (10, 20, 30))
    }
  }
}
