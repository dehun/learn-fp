package learnfp.comonad

import org.scalatest.{Matchers, WordSpecLike}

import learnfp.comonad.Store._
import learnfp.comonad.CoMonadOps._

class StoreTest extends WordSpecLike with Matchers {
  type IntStore[A] = Store[A, Int]
  def intStore[A](x:A)(fx:A => Int):IntStore[A] = Store[A, Int](x, fx)

  "Store" should {
    "work" in {
      //intStore[String]("10") {x:String => x.toInt} coFlatMap {x:IntStore[String] => x.peek()}
    }
  }
}
