package learnfp.applicative

import org.scalatest.{Matchers, WordSpecLike}
import learnfp.functor.State
import learnfp.functor.State._
import learnfp.functor.StateInstance._
import learnfp.applicative.StateInstance._

class StateTest extends WordSpecLike with Matchers {
  "state applicative" should {
    "work" in {
      {
        { (x: Int, y: Int, z: Int) => (x, y, z) }.curried `<$>` countAndRemove('a') <*> 20.pure[String] <*> countAndRemove('b')
      }.run("abracadabra") shouldBe("rcdr", (5, 20, 2))
    }
  }

  private def countAndRemove(char: Char): State[String, Int] = State { (s: String) =>
    val (chars, other) = s.partition(_ == char)
    (other, chars.size)
  }
}
