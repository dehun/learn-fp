package learnfp.comonad

import org.scalatest.{Matchers, WordSpecLike}
import learnfp.comonad.CoMonadOps._
import learnfp.comonad.IdInstance._
import learnfp.functor.Id

class IdTest extends WordSpecLike with Matchers {
  "Id comonad" should {
    "work" in {
      { Id(1) coFlatMap {x:Id[Int]=> x.value + 3} counit} shouldBe 4
    }
  }
}
