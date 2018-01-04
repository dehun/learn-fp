package learnfp.monoid

import org.scalatest.{Matchers, WordSpecLike}

class PairAdditiveMonoidTest extends WordSpecLike with Matchers {
  import MonoidOps._
  import PairAdditiveMonoid._
  import SimpleMonoid._
  import ListMonoid._

  "pair monoid" should {
    "operate on nested structures" in {
      Pair(Pair(Sum(1), Product(2)), Pair(List(3, 33, 333), Sum(4))) |+|
        Pair(Pair(Sum(21), Product(22)), Pair(List(23, 233, 2333), Sum(24))) shouldBe
        Pair(Pair(Sum(1 + 21), Product(2 * 22)), Pair(List(3, 33, 333, 23, 233, 2333), Sum(4 + 24)))
    }
    "obey identity" in {
      Pair(Sum(10), Product(20)) |+| Monoid.mzero[Pair[Sum, Product]] shouldBe Pair(Sum(10), Product(20))
      Pair(List(1, 2, 3), Sum(1)) |+| Monoid.mzero[Pair[List[Int], Sum]] shouldBe Pair(List(1, 2, 3), Sum(1))
      Pair(Pair(Sum(1), Sum(2)), Pair(Sum(3), Sum(4))) |+|
        Monoid.mzero[Pair[Pair[Sum, Sum], Pair[Sum, Sum]]] shouldBe Pair(Pair(Sum(1), Sum(2)), Pair(Sum(3), Sum(4)))
    }

    "obey associtiativity" in {
      val a = Pair(Pair(Sum(1), Product(2)), Pair(List(3, 33, 333), Sum(4)))
      val b = Pair(Pair(Sum(21), Product(22)), Pair(List(23, 233, 2333), Sum(24)))
      val c = Pair(Pair(Sum(31), Product(32)), Pair(List(33, 333, 3333), Sum(34)))
      a |+| b |+| c shouldBe a |+| (b |+| c)
    }
  }
}
