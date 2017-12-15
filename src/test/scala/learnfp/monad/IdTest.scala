package learnfp.monad

import learnfp.functor.Id
import org.scalatest.{Matchers, WordSpecLike}
import learnfp.monad.IdInstance._
import learnfp.functor.IdInstance._
import learnfp.monad.MonadOps._
import learnfp.monad.IdInstance._

class IdTest extends WordSpecLike with Matchers {
  "id monad" should {
    "work" in {
      { 10.pure[Id] >>= {x:Int => Id(x + 10)} } shouldBe 20.pure[Id]
    }
  }
}
