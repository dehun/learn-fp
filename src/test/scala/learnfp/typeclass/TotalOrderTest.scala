package learnfp.typeclass

import org.scalatest.{FlatSpec, Matchers}

class TotalOrderTest extends FlatSpec with Matchers {
  import TotalOrderInstances._
  "total order" should "compare ints" in {
    Comparator.less(5, 10) shouldBe true
    Comparator.less(10, 5) shouldBe false
  }

  "total order" should "compare strings" in {
    Comparator.less("5", "9") shouldBe true
    Comparator.less("9", "5") shouldBe false
  }

  "total order" should "compare list of ints" in {
    Comparator.less(List(5, 10), List(10, 20)) shouldBe true
  }
}
