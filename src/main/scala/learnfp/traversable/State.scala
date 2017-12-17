package learnfp.traversable

import learnfp.functor.Functor
import learnfp.functor.State
import learnfp.functor.State._

object StateInstance {
  implicit def stateListToTraversableOps[S, A](xs:List[State[S, A]])(
    implicit functor:Functor[({type E[X] = State[S, X]})#E]) = new TraversableOps[A, ({type E[X] = State[S, X]})#E](xs)
}
