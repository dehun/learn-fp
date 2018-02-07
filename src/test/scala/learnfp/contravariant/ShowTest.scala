package learnfp.contravariant

import learnfp.contravariant.ContravariantFunctorOps._
import org.scalatest.{Matchers, WordSpecLike}

class ShowTest extends WordSpecLike with Matchers {
  "Show" should {
    "work" in {
      { Show {x:Int => x.toString} cmap
        {f:Double => f.floor.toInt} cmap
        {z:String => z.toDouble} }.show("3.14") shouldBe "3"
    }
    "obey identity" in {
      { Show {x:Int => x.toString} cmap identity[Int] }.show(3) shouldBe "3"
    }
    "obey composition" in {
      val f = { x:Double => x.floor.toInt }
      val g = { x:String => x.reverse.toDouble }
      val intShow = Show {x:Int => x.toString}
      val lshow = intShow cmap f cmap g
      val rshow = intShow cmap {x:String => f(g(x))}
      lshow.show("123.123") shouldBe rshow.show("123.123")
    }
  }
}
