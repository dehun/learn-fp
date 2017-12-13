package learnfp.applicative

import org.scalatest.{Matchers, WordSpecLike}

class DisjunctionTest extends WordSpecLike with Matchers {
  import learnfp.functor.Disjunction._
  import learnfp.functor.DisjunctionInstance._
  import learnfp.applicative.DisjunctionInstance._

  "disjunction applicative" should {
    def rght(x:Int):Disjunction[String, Int] = right[String, Int](x);

    "work with right" in {
      {(x:Int, y:Int, z:Int) => x + y + z}.curried `<$>` rght(10) <*> rght(20) <*> rght(30) shouldBe rght(10 + 20 + 30)
    }

    "work with left" in {
      {(x:Int, y:Int, z:Int) => x + y + z}.curried `<$>` rght(10) <*> left[String, Int]("boom") <*> rght(30) shouldBe left[String, Int]("boom")
    }
  }
}
