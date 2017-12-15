package learnfp.traversable

import learnfp.functor.Functor
import learnfp.functor.Disjunction._

object DisjunctionInstance {
  implicit def disjunctionListToTraversableOps[A, L, D[_, _] <: Disjunction[L, A]](xs:List[Disjunction[L, A]])(
    implicit functor:Functor[({type E[X] = Disjunction[L, X]})#E]) = new TraversableOps[A, ({type E[X] = Disjunction[L, X]})#E](xs)
}
