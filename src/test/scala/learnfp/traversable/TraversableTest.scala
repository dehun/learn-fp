package learnfp.traversable

import learnfp.traversable.TraversableInstances._
import learnfp.traversable.TraversableOps._
import learnfp.foldable.FoldableInstances._
import learnfp.foldable.FoldableOps._
import org.scalatest.{Matchers, WordSpecLike}

class TraversableTest extends WordSpecLike with Matchers {

  import learnfp.functor.Id
  import learnfp.functor.IdInstance._
  import learnfp.applicative.IdInstance._

  "traversable" should {
    "traverse Id[Id]" in {
      { Id(Id(1)) traverse {_ * 2} } shouldBe Id(Id(2))
    }

    "sequence Id[Id]" in {
      { Id(Id(1)) sequence } shouldBe Id(Id(1))
    }

    "traverse Tuple3[Id]" in {
      { stuple3(Id(1), Id(2), Id(3)) traverse {_ * 2} } shouldBe (Id(2, 4, 6))
    }

    "sequence Tuple3[Id]" in {
      { stuple3(Id(1), Id(2), Id(3)) sequence } shouldBe (Id(1, 2, 3))
    }

    "sequence List[Id]" in {
      { List(Id(1), Id(2), Id(3), Id(4)) sequence } shouldBe Id(List(1, 2, 3, 4))
    }

    "traverse List[Id]" in {
      { List(Id(1), Id(2), Id(3), Id(4)) traverse {_ * 2} } shouldBe Id(List(2, 4, 6, 8))
    }

    import learnfp.functor.Maybe._
    import learnfp.functor.MaybeInstance._
    import learnfp.applicative.MaybeInstance._

    "sequence Id[Maybe]" in {
      { Id(just(5)) sequence } shouldBe just(Id(5))
    }

    "traverse Id[Maybe]" in {
      { Id(just(5)) traverse {_ + 1} } shouldBe just(Id(6))
    }

    "sequence Tuple3[Maybe]" in {
      { stuple3(just(5), just(6), just(7)) sequence } shouldBe just(5, 6, 7)
      (stuple3(just(5), nothing[Int](), just(7)).sequence) shouldBe nothing()
    }

    "traverse Tuple3[Maybe]" in {
      { stuple3(just(5), just(6), just(7)) traverse {_ + 1} } shouldBe just(6, 7, 8)
      ( stuple3(just(5), nothing[Int](), just(7)) traverse {_ + 1} ) shouldBe nothing()
    }

    "sequence List[Maybe]" in {
      { List(just(5), just(10), just(3)) sequence } shouldBe just(List(5, 10, 3))
      ( List(just(5), nothing[Int](), just(3)) sequence ) shouldBe nothing[Int]()
    }

    "traverse List[Maybe]" in {
      { List(just(5), just(10), just(3)) traverse {_ + 1} } shouldBe just(List(6, 11, 4))
      ( List(just(5), nothing[Int](), just(3)) traverse {_ + 1} ) shouldBe nothing[Int]()
    }

    import learnfp.functor.ListInstance._
    import learnfp.applicative.ListInstance._

    "traverse Id[List]" in {
      Id(List(1, 2, 3)) traverse {_ + 1} shouldBe List(Id(2), Id(3), Id(4))
    }

    "sequence Id[List]" in {
      { Id(List(1, 2, 3)) sequence } shouldBe List(Id(1), Id(2), Id(3))
    }

    "sequence Tuple3[List]" in {
      ( stuple3(List(1, 2), List(3), List(4, 5)) sequence ) shouldBe List((1, 3, 4), (1, 3, 5), (2, 3, 4), (2, 3, 5))
    }

    "traverse Tuple3[List]" in {
      ( stuple3(List(1, 2), List(3), List(4, 5)) traverse { _ + 1} ) shouldBe List((2, 4, 5), (2, 4, 6), (3, 4, 5), (3, 4, 6))
    }

    "sequence List[List]" in {
      { List(List(1), List(2, 3), List(4, 5, 6)) sequence} shouldBe List(
        List(1, 2, 4), List(1, 2, 5), List(1, 2, 6),
        List(1, 3, 4), List(1, 3, 5), List(1, 3, 6))
    }

    "traverse List[List]" in {
      { List(List(1), List(2, 3), List(4, 5, 6)) traverse { _ + 1 } } shouldBe List(
        List(2, 3, 5), List(2, 3, 6), List(2, 3, 7),
        List(2, 4, 5), List(2, 4, 6), List(2, 4, 7))
    }

    import learnfp.functor.Disjunction._
    import learnfp.functor.DisjunctionInstance._
    import learnfp.applicative.DisjunctionInstance._

    type StringDisjunction[A] = Disjunction[String, A]
    def r(x:Int):StringDisjunction[Int] = right[String, Int](x)
    def l(x:String):StringDisjunction[Int] = left[String, Int](x)

    "sequence List[Disjunction]" in {
      { List(r(5), r(6), r(7)) sequence } shouldBe right[String, List[Int]](List(5, 6, 7));
      { List(r(5), l("boom"), r(7)) sequence } shouldBe left[String, List[Int]]("boom");
      { List(r(5), l("boom"), l("baam")) sequence } shouldBe left[String, List[Int]]("boom")
    }

    "traverse List[Disjunction]" in {
      { List(r(5), r(6), r(7)) traverse {_ + 1} } shouldBe right[String, List[Int]](List(6, 7, 8));
      { List(r(5), l("boom"), r(7)) traverse {_ + 1} } shouldBe left[String, List[Int]]("boom");
      { List(r(5), l("boom"), l("baam")) traverse {_ + 1} } shouldBe left[String, List[Int]]("boom")
    }

    import learnfp.functor.State
    import learnfp.functor.State._
    import learnfp.functor.StateInstance._
    import learnfp.applicative.StateInstance._
    type StringState[A] = State[String, A]
    def stringState[A](fx:String => (String, A)):StringState[A] = State[String, A](fx)

    "sequence Id[State]" in {
      { Id(stringState {s:String => (s + " boom", 6)}) sequence }.run("baam") shouldBe ("baam boom", Id(6))
    }

    "traverse Id[State]" in {
      { Id(stringState {s:String => (s + " boom", 6)}) traverse { _ + 2} }.run("baam") shouldBe ("baam boom", Id(8))
    }

    "sequence Tuple3[State]" in {
      { stuple3(
        stringState {s:String => (s + " een", 1)},
        stringState {s:String => (s + " twee", 2)},
        stringState {s:String => (s + " drie", 3)}) sequence }.run("null") shouldBe ("null een twee drie", (1, 2, 3))
    }

    "traverse Tuple3[State]" in {
      { stuple3(
        stringState {s:String => (s + " een", 1)},
        stringState {s:String => (s + " twee", 2)},
        stringState {s:String => (s + " drie", 3)}) traverse { _  + 1 } }.run("null") shouldBe ("null een twee drie", (2, 3, 4))
    }

    "sequence List[State]" in {
       { List(5.pure[String], stringState {s:String => (s + "boom", 6)}, 7.pure[String]) sequence }.run("baam ") shouldBe ("baam boom", List(5, 6, 7))
    }

    "traverse List[State]" in {
      { List(5.pure[String], stringState {s:String => (s + "boom", 6)}, 7.pure[String]) traverse {x:Int => x * 2} }.run("baam ") shouldBe ("baam boom", List(10, 12, 14))
    }

    import learnfp.functor.Writer
    import learnfp.functor.Writer._
    import learnfp.functor.WriterInstance._
    import learnfp.applicative.WriterInstance._
    import learnfp.monoid.ListMonoid._
    type StringWriter[A] = Writer[List[String], A];

    def stringWriter[A](fx:() => (List[String], A)):StringWriter[A] = {
      Writer[List[String], A](fx)
    }

    "sequence Id[Writer]" in {
      { Id(stringWriter({() => (List("een"), 1)})) sequence }.run() shouldBe (List("een"), Id(1))
    }

    "traverse Id[Writer]" in {
      { Id(stringWriter({() => (List("een"), 1)})) traverse {_ * 2} }.run() shouldBe (List("een"), Id(2))
    }

    "sequence Tuple3[Writer]" in {
      { stuple3(
        stringWriter({() => (List("een"), 1)}),
        stringWriter({() => (List("twee"), 2)}),
        stringWriter({() => (List("drie"), 3)})) sequence }.run() shouldBe (List("een", "twee", "drie"), (1, 2, 3))
    }

    "traverse Tuple3[Writer]" in {
      { stuple3(
        stringWriter({() => (List("een"), 1)}),
        stringWriter({() => (List("twee"), 2)}),
        stringWriter({() => (List("drie"), 3)})) traverse { _ * 2 } }.run() shouldBe (List("een", "twee", "drie"), (2, 4, 6))
    }

    "sequence List[Writer]" in {
      {List(stringWriter {() => (List("een"), 1)}, stringWriter {() => (List("twee"), 2)}, stringWriter {() => (List("drie"), 3)}) sequence}.run() shouldBe (
        List("een", "twee", "drie"), List(1, 2, 3))
    }

    "traverse List[Writer]" in {
      {List(stringWriter {() => (List("een"), 1)}, stringWriter {() => (List("twee"), 2)}, stringWriter {() => (List("drie"), 3)}) traverse {x:Int => x + 1}}.run() shouldBe (
        List("een", "twee", "drie"), List(2, 3, 4))
    }
  }
}
