package learnfp.foldable

trait Foldable[C[_]] {
  def foldr[A, B](xs:C[A])(init:B)(fx:(A, B) => B):B
}

object FoldableInstances {
  implicit def listFoldable = new Foldable[List] {
    override def foldr[A, B](xs: List[A])(init: B)(fx: (A, B) => B): B = xs.foldRight(init)(fx)
  }

  implicit def tuple2Foldable = new Foldable[({type E[X] = (X, X)})#E] {
    override def foldr[A, B](xs: (A, A))(init: B)(fx: (A, B) => B): B = {
      val fst = fx(xs._1, init)
      val snd = fx(xs._2, fst)
      snd
    }
  }

  implicit def tuple3Foldable = new Foldable[({type E[X] = (X, X, X)})#E] {
    override def foldr[A, B](xs: (A, A, A))(init: B)(fx: (A, B) => B): B = {
      List(xs._1, xs._2, xs._3).foldRight(init)(fx) // super lazy...
    }
  }
}

class FoldableOps[C[_], A](xs:C[A])(implicit foldable: Foldable[C]) {
  def myfoldr[B](init:B)(fx:(A, B) => B):B = foldable.foldr(xs)(init)(fx)
}

object FoldableOps {
  implicit def toFoldableOps[C[_], A](xs:C[A])(implicit foldable: Foldable[C]) = new FoldableOps[C, A](xs)
}
