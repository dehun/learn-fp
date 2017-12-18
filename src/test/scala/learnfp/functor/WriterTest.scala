package learnfp.functor

import org.scalatest.{Matchers, WordSpecLike}
import learnfp.functor.Writer._
import learnfp.functor.WriterInstance._
import learnfp.monoid.ListMonoid._

class WriterTest extends WordSpecLike with Matchers {
  "writer functor" should {
    "work" in {
      { Writer({() => (List("een"), 10)}) fmap {x:Int => x + 20} }.run() shouldBe (List("een"), 30)
    }
    "obey identity" in {
      { Writer({() => (List("een"), 10)}) fmap identity }.run() shouldBe (List("een"), 10)
    }
    "obey composition" in {
      val f = {x:Int => x + 1}
      val g = {x:Int => x * 2}
      val a = { Writer({() => (List("een"), 10)}) fmap f fmap g }
      { a fmap f fmap g }.run() shouldBe { a fmap (f andThen g)}.run()
    }
  }
}
