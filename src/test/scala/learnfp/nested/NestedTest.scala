package learnfp.nested

import learnfp.applicative.Applicative
import learnfp.functor.{Functor, Id, Maybe}
import org.scalatest.{Matchers, WordSpecLike}
import learnfp.functor.FunctorOps._
import learnfp.functor.IdInstance._
import learnfp.applicative.IdInstance._
import learnfp.applicative.ListInstance._
import learnfp.functor.Maybe.{Maybe, just, nothing}
import learnfp.applicative.MaybeInstance.maybeApplicativeInstance
import learnfp.functor.ListInstance._
import learnfp.functor.MaybeInstance._
import learnfp.applicative.ApplicativeOps._

class NestedTest extends WordSpecLike with Matchers {
  "Nested" should {
    // nest functors
    type NestedId[A] = Nested[Id, Id, A]
    "nest Id functor" in {
      val x:NestedId[Int] = Nested(Id(Id(5)))
      x.fmap {x:Int => x +1 }.value shouldBe Id(Id(6))
    }

    type NestedListMaybe[A] = Nested[List, Maybe, A]
    "nest List and Maybe functors" in {
      val x:NestedListMaybe[Int] = Nested(List(just(5), nothing[Int], just(3)))
      x.fmap(_ + 1).value shouldBe List(just(6), nothing[Int], just(4))
    }

    // nest applicatives
    "nest Id applicative" in {
      val x:NestedId[Int] = Nested(Id(Id(5)))
      val y:NestedId[Int] = Nested(Id(Id(6)));
      { { (a:Int, b:Int) => a + b}.curried `<$>` x `<*>` y } shouldBe implicitly[Applicative[NestedId]].pure(11)
    }

    "nest List and Maybe applicatives" in {
      val x:NestedListMaybe[Int] = Nested(List(just(1), nothing[Int], just(3)))
      val y:NestedListMaybe[Int] = Nested(List(just(4), nothing[Int], just(6)));
      { { (a:Int, b:Int) => a + b }.curried `<$>` x `<*>` y }.value shouldBe  List(
        just(5), nothing[Int], just(7),
        nothing[Int], nothing[Int], nothing[Int],
        just(7), nothing[Int], just(9))
    }

    // when we got applicatives we can traverse
    import learnfp.traversable.TraversableInstances._
    import learnfp.traversable.TraversableOps._
    import learnfp.traversable.SequenceOps._
    import learnfp.foldable.FoldableInstances._

    "traverse with Id[Id[_]]" in {
      import learnfp.traversable.TraversableInstances._
      import learnfp.traversable.TraversableOps._
      import learnfp.foldable.FoldableInstances._
      List(5, 6).traverse { x:Int => Nested(Id(Id(x + 1))):NestedId[Int] }.value shouldBe Id(Id(List(6, 7)))
    }

    "traverse with List[Maybe[_]]" in { // TODO: check me
      val x:NestedListMaybe[Int] = Nested(List(just(1), nothing[Int], just(3)))
      val y:NestedListMaybe[Int] = Nested(List(just(4), just(5)));
      { List(1, 2) traverse {
        case 1 => x
        case 2 => y
      } }.value shouldBe List(
        just(List(1, 4)), just(List(1, 5)),
        nothing[List[Int]], nothing[List[Int]],
        just(List(3, 4)), just(List(3, 5)))
    }

    "sequence with List[Maybe[_]]" in {
      val x:NestedListMaybe[Int] = Nested(List(just(1), nothing[Int], just(3)))
      val y:NestedListMaybe[Int] = Nested(List(just(4), just(5)));
      List(x, y).sequence.value shouldBe List(
        just(List(1, 4)), just(List(1, 5)),
        nothing[List[Int]], nothing[List[Int]],
        just(List(3, 4)), just(List(3, 5)))
    }
  }
}
