package learnfp.foldable

import org.scalatest.{Matchers, WordSpecLike}

import FoldableInstances._
import FoldableOps._
import learnfp.functor.Id

class FoldableTest extends WordSpecLike with Matchers {
  "Foldable" should {

    "work with id" in {
      Id("a").myfoldr("b")((x, a) => a + x) shouldBe "ba"
    }

    "work with list" in {
      List("a", "b", "c").myfoldr("init"){(x, a) => a + x} shouldBe ("initcba")
    }

    "work with tuples" in {
      ("a", "b").myfoldr("init"){(x, a) => a + x} shouldBe ("initba")
      ("a", "b", "c").myfoldr("init"){(x, a) => a + x} shouldBe ("initcba")
    }

    import learnfp.functor.Maybe._
    "work with Maybe" in {
      just(1).myfoldr(0) {(x, a) => a + x } shouldBe 1
      nothing[Int]().myfoldr(0) {(x, a) => a + x } shouldBe 0
    }

    import learnfp.functor.Disjunction._
    "work with disjunction" in {
      right[String, String]("abc").myfoldr("init"){(x, a) => a + x} shouldBe "initabc"
      left[String, Int]("foo").myfoldr("init"){(x, a) => a + x} shouldBe "init"
    }
  }
}
