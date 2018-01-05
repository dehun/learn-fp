package learnfp.typeclass

import org.scalatest.{Matchers, WordSpecLike}
import EqOps._
import EqInstances._

class EqTest extends WordSpecLike with Matchers {
  "Eq should" should {
    "eq ints" in {
      1 ==== 1 shouldBe true
      1 ==== 2 shouldBe false
    }

    "eq strings" in {
      "asd" ==== "asd" shouldBe true
      "dsa" ==== "qwerty" shouldBe false
    }

    "eq lists of ints" in {
      List(1, 2, 3) ==== List(1, 2, 3) shouldBe true
      List(1, 2, 3) ==== List(3, 4, 5) shouldBe false
      List(1, 2) ==== List(1, 2, 3) shouldBe false
      List(1, 2, 3) ==== List(1, 2) shouldBe false
    }

    "eq lists of strings" in {
      List("aa", "bbb", "ccc") ==== List("aa", "bbb", "ccc") shouldBe true
      List("aa", "bbb", "ccc") ==== List("a", "bb", "cc") shouldBe false
    }

    "regular == should compares different types" in {
      "asd" == 5 shouldBe false
      List(1, 2, 3) == 5 shouldBe false
    }

    "our super ==== should not compare different types" in {
      """"asd" ==== 5 shouldBe false""" shouldNot typeCheck
      """List(1, 2, 3) ==== List("a", "b", "c")""" shouldNot typeCheck
    }
  }
}
