package learnfp.comonad

import org.scalatest.{Matchers, WordSpecLike}
import learnfp.functor.FunctorOps._
import learnfp.comonad.CoMonadOps._
import learnfp.comonad.Env._

class EnvTest extends WordSpecLike with Matchers {
  "Env" should {
    type StringEnv[A] = Env[A, String]
    def stringEnv[A](a:A, s:String) = Env[A, String](a, s)

    "functor work" in {
      { stringEnv(1, "een") map {x:Int => x + 2} counit } shouldBe 3
    }

    "monad work" in {
      { stringEnv(1, "something") coFlatMap {e:StringEnv[Int] => e.a + e.r.length} counit } shouldBe 10
    }
  }
}
