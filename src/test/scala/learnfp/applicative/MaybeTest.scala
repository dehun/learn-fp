package learnfp.applicative

import org.scalatest.{Matchers, WordSpecLike}


class MaybeTest extends WordSpecLike with Matchers {
  import learnfp.functor.{Maybe, MaybeInstance => MaybeFunctorInstance}
  import Maybe._
  import MaybeFunctorInstance._
  import learnfp.functor.FunctorOps._
  import learnfp.applicative.ApplicativeOps._
  import learnfp.applicative.MaybeInstance._

  "maybe applicative" should {
    "works with just" in {
      { (x:Int, y:Int) =>  x + y }.curried `<$>` just(5) <*> just(10) shouldBe just(15)
    }

    "works with nothing" in {
      { (x:Int, y:Int) =>  x + y }.curried `<$>` nothing[Int]() <*> just(10) shouldBe nothing[Int]();
      { (x:Int, y:Int) =>  x + y }.curried `<$>` just(5) <*> nothing[Int]() shouldBe nothing[Int]()
    }
  }
}
