package learnfp.io

import org.scalatest.{Matchers, WordSpecLike}
import learnfp.functor.FunctorOps._
import learnfp.applicative.ApplicativeOps._
import learnfp.monad.MonadOps._

class IOTest extends WordSpecLike with Matchers {
  "IO" should {
    "read same file twice" in {
      var toyFileContent:String = "initial content"
      def writeFile(newContent:String):IO[Unit] = IO { () => toyFileContent = newContent }
      def readFile():IO[String] = IO { () => toyFileContent }

      val file = readFile()

      val computation = for {
        oldContent <- file
        _ <- writeFile(oldContent + "; something new")
        newContent <- file
      } yield (oldContent, newContent)
      val (o, n) = IO.runIO(computation)
      o should not be(n)
    }

    "execute in proper order" in {
      var order = List.empty[Char]
      def foo(x:Int, y:Int, z:Int) = x + y  + z
      def getX() = IO { () => order = 'x'::order; 1 }
      def getY() = IO { () => order = 'y'::order; 2 }
      def getZ() = IO { () => order = 'z'::order; 3 }
      val computation = (foo _).curried `<$>` getX() `<*>` getY `<*>` getZ
      IO.runIO(computation) shouldBe 6
      order shouldBe List('z', 'y', 'x')
    }
  }
}
