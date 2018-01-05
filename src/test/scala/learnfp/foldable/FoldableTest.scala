package learnfp.foldable

import org.scalatest.{Matchers, WordSpecLike}

import FoldableInstances._
import FoldableOps._

class FoldableTest extends WordSpecLike with Matchers {
  "Foldable" should {
    "work with list" in {
      List("a", "b", "c").myfoldr("init"){(x, a) => a + x} shouldBe ("initcba")
    }

    "work with tuples" in {
      toFoldableOps(("a", "b")).myfoldr("init"){(x, a) => a + x} shouldBe ("initba")
    }
  }
}
