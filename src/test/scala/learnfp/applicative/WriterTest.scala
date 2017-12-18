package learnfp.applicative

import org.scalatest.{Matchers, WordSpecLike}

import learnfp.functor.Writer
import learnfp.functor.WriterInstance.{writerInstance, writerToFunctorFxOps, writerToFunctorOps}
import learnfp.applicative.WriterInstance.{toPureOps, writerApplicativeInstance, writerToApplicativeOps}
import learnfp.monoid.ListMonoid._

class WriterTest extends WordSpecLike with Matchers {
  "writer applicative" should {
    "work" in {
      { {(x:Int, y:Int, z:Int) => (x, y, z)}.curried `<$>`
          Writer {() => (List("een"), 1)} <*>
          Writer {() => (List("twee"), 2)} <*>
          Writer {() => (List("dree"), 3)} }.run() shouldBe (List("een", "twee", "dree"), (1, 2, 3))
    }
  }
}
