package learnfp.foldable

import learnfp.functor.Disjunction.{Disjunction, LeftDisjunction, RightDisjunction}
import learnfp.functor.Maybe.{Just, Maybe, Nothing}

trait Foldable[C[_]] {
  def foldr[A, B](xs:C[A])(init:B)(fx:(A, B) => B):B
}

object FoldableInstances {
  implicit def listFoldable = new Foldable[List] {
    override def foldr[A, B](xs: List[A])(init: B)(fx: (A, B) => B): B = xs.foldRight(init)(fx)
  }

  implicit def tuple2Foldable = new Foldable[({type E[X] = Tuple2[X, X]})#E] {
    override def foldr[A, B](xs: (A, A))(init: B)(fx: (A, B) => B): B = {
      fx(xs._1, fx(xs._2, init))
    }
  }

  implicit def tuple3Foldable = new Foldable[({type E[X] = (X, X, X)})#E] {
    override def foldr[A, B](xs: (A, A, A))(init: B)(fx: (A, B) => B): B = {
      fx(xs._1, fx(xs._2, fx(xs._3, init)))
    }
  }

  implicit val maybeFoldable = new Foldable[Maybe] {
    override def foldr[A, B](xs: Maybe[A])(init: B)(fx: (A, B) => B): B = xs match {
      case Just(x) => fx(x, init)
      case Nothing() => init
    }
  }

  implicit def disjunctionFoldable[L] = new Foldable[({type E[X] = Disjunction[L, X]})#E] {
    override def foldr[A, B](xs: Disjunction[L, A])(init: B)(fx: (A, B) => B): B = xs match {
      case RightDisjunction(x) => fx(x, init)
      case LeftDisjunction(_) => init
    }
  }
}

class FoldableOps[C[_], A](xs:C[A])(implicit foldable: Foldable[C]) {
  def myfoldr[B](init:B)(fx:(A, B) => B):B = foldable.foldr(xs)(init)(fx)
}

object FoldableOps {
  implicit def toFoldableOps[C[_], A](xs:C[A])(implicit foldable: Foldable[C]) = new FoldableOps[C, A](xs)
  implicit def tuple2ToFoldableOps[A](xs:(A, A))(implicit foldable:Foldable[({type E[X] = (X, X)})#E]) = new FoldableOps[({type E[X] = (X, X)})#E, A](xs)
  implicit def tuple3ToFoldableOps[A](xs:(A, A, A))(implicit foldable:Foldable[({type E[X] = (X, X, X)})#E]) = new FoldableOps[({type E[X] = (X, X, X)})#E, A](xs)
  implicit def disjunctionToFoldableOps[L, A](xs:Disjunction[L, A])(implicit foldable:Foldable[({type E[X] = Disjunction[L, X]})#E]) =
    new FoldableOps[({type E[X] = Disjunction[L, X]})#E, A](xs)

}
