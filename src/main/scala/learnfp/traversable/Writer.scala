package learnfp.traversable

import learnfp.functor.WriterInstance._
import learnfp.functor.{Functor, Writer}

object WriterInstance {
  implicit def writerListToTraversableOps[W, A](xs:List[Writer[W, A]])(
    implicit functor:Functor[({type E[X] = Writer[W, X]})#E]) = new TraversableOps[A, ({type E[X] = Writer[W, X]})#E](xs)
}