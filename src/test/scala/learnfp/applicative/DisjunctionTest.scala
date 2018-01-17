package learnfp.applicative

import org.scalatest.{Matchers, WordSpecLike}

class DisjunctionTest extends WordSpecLike with Matchers {
  import learnfp.functor.Disjunction._
  import learnfp.functor.DisjunctionInstance._
  import learnfp.applicative.DisjunctionInstance._

  "disjunction applicative" should {
    def right(x:Int):Disjunction[String, Int] = RightDisjunction(x)

    "work with right" in {
      {(x:Int, y:Int, z:Int) => x + y + z}.curried `<$>` right(10) <*> right(20) <*> right(30) shouldBe right(10 + 20 + 30)
    }

    "work with left" in {
      {(x:Int, y:Int, z:Int) => x + y + z}.curried `<$>` right(10) <*> left[String, Int]("boom") <*> right(30) shouldBe left[String, Int]("boom")
    }
  }
}
