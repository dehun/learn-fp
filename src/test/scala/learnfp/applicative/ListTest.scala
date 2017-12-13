package learnfp.applicative

import org.scalatest.{Matchers, WordSpecLike}

class ListTest extends WordSpecLike with Matchers {
  import learnfp.applicative.ApplicativeOps._
  import learnfp.applicative.ListInstance._
  import learnfp.functor.FunctorOps._
  import learnfp.functor.ListInstance._

  "list applicative" should {
    "work" in {
      {(x:Int, y:Int) => (x, y)}.curried `<$>` List(1,2) <*> List(3, 4, 5) shouldBe List(
        (1, 3), (1, 4), (1, 5),
        (2, 3), (2, 4), (2, 5))

      {(x:Int, y:Int, z:String) => (x, y, z)}.curried `<$>` List(1,2) <*> List(3, 4) <*> List("5", "6") shouldBe List(
        (1, 3, "5"), (1, 3, "6"),
        (1, 4, "5"), (1, 4, "6"),
        (2, 3, "5"), (2, 3, "6"),
        (2, 4, "5"), (2, 4, "6"))
    }
  }
}
