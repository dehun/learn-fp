package learnfp.applicative

import org.scalatest.{Matchers, WordSpecLike}

import learnfp.functor.Id
import learnfp.functor.FunctorOps._
import learnfp.functor.IdInstance._
import learnfp.applicative.ApplicativeOps._
import learnfp.applicative.IdInstance._

class IdTest extends WordSpecLike with Matchers {
  "id applicative" should {
    "work" in {
      def foo(x:Int)(y:Int)(z:Int) = x + y + z
      foo _ `<$>` Id(10) <*> Id(20) <*> Id(30) shouldBe Id(10 + 20 + 30)
    }
  }
}
