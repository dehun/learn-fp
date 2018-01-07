package learnfp.traversable

import learnfp.traversable.TraversableInstances._
import learnfp.traversable.TraversableOps._
import learnfp.foldable.FoldableInstances._
import org.scalatest.{Matchers, WordSpecLike}

class TraversableTest extends WordSpecLike with Matchers {

  import learnfp.functor.Id
  import learnfp.functor.IdInstance._
  import learnfp.applicative.IdInstance._

  "traversable" should {
    "sequence Id" in {
      { toTraversableOps(List(Id(1), Id(2), Id(3), Id(4))) sequence } shouldBe Id(List(1, 2, 3, 4))
    }

    "traverse Id" in {
      { List(Id(1), Id(2), Id(3), Id(4)) traverse {_ * 2} } shouldBe Id(List(2, 4, 6, 8))
    }

    import learnfp.functor.Maybe._
    import learnfp.functor.MaybeInstance._
    import learnfp.applicative.MaybeInstance._

    "sequence Maybe" in {
      { List(just(5), just(10), just(3)) sequence } shouldBe just(List(5, 10, 3))
    }

    "traverse Maybe" in {
      { List(just(5), just(10), just(3)) traverse {_ + 1} } shouldBe just(List(6, 11, 4))
    }

    import learnfp.functor.ListInstance._
    import learnfp.applicative.ListInstance._

    "sequence List" in {
      { List(List(1), List(2, 3), List(4, 5, 6)) sequence} shouldBe List(
        List(1, 2, 4), List(1, 2, 5), List(1, 2, 6),
        List(1, 3, 4), List(1, 3, 5), List(1, 3, 6))
    }

    "traverse List" in {
      { List(List(1), List(2, 3), List(4, 5, 6)) traverse { _ + 1 } } shouldBe List(
        List(2, 3, 5), List(2, 3, 6), List(2, 3, 7),
        List(2, 4, 5), List(2, 4, 6), List(2, 4, 7))
    }

//    import learnfp.functor.Disjunction._
//    import learnfp.functor.DisjunctionInstance._
//    import learnfp.applicative.DisjunctionInstance._
//
//    def r(x:Int) = right[String, Int](x)
//    def l(x:String) = left[String, Int](x)
//
//    "sequence Disjunction" in {
//      { List(r(5), r(6), r(7)) sequence } shouldBe right[String, List[Int]](List(5, 6, 7));
//      { List(r(5), l("boom"), r(7)) sequence } shouldBe left[String, List[Int]]("boom");
//      { List(r(5), l("boom"), l("baam")) sequence } shouldBe left[String, List[Int]]("boom")
//    }
//
//    "traverse Disjunction" in {
//      { List(r(5), r(6), r(7)) traverse {_ + 1} } shouldBe right[String, List[Int]](List(6, 7, 8));
//      { List(r(5), l("boom"), r(7)) traverse {_ + 1} } shouldBe left[String, List[Int]]("boom");
//      { List(r(5), l("boom"), l("baam")) traverse {_ + 1} } shouldBe left[String, List[Int]]("boom")
//    }
//
//    import learnfp.functor.State
//    import learnfp.functor.State._
//    import learnfp.functor.StateInstance._
//    import learnfp.applicative.StateInstance._
//    import learnfp.traversable.StateInstance._
//
//    "sequence State" in {
//       { List(5.pure[String], State {s:String => (s + "boom", 6)}, 7.pure[String]) sequence }.run("baam ") shouldBe ("baam boom", List(5, 6, 7))
//    }
//
//    "traverse State" in {
//      { List(5.pure[String], State {s:String => (s + "boom", 6)}, 7.pure[String]) traverse {x:Int => x * 2} }.run("baam ") shouldBe ("baam boom", List(10, 12, 14))
//    }
//
//    import learnfp.functor.Writer
//    import learnfp.functor.Writer._
//    import learnfp.functor.WriterInstance._
//    import learnfp.applicative.WriterInstance._
//    import learnfp.traversable.WriterInstance._
//    import learnfp.monoid.ListMonoid._
//    type StringWriter[A] = Writer[List[String], A];
//
//    "sequence Writer" in {
//      {List(Writer {() => (List("een"), 1)}, Writer {() => (List("twee"), 2)}, Writer {() => (List("drie"), 3)}) sequence}.run() shouldBe (
//        List("een", "twee", "drie"), List(1, 2, 3))
//    }
//
//    "traverse Writer" in {
//      {List(Writer {() => (List("een"), 1)}, Writer {() => (List("twee"), 2)}, Writer {() => (List("drie"), 3)}) traverse {x:Int => x + 1}}.run() shouldBe (
//        List("een", "twee", "drie"), List(2, 3, 4))
//    }
  }
}
