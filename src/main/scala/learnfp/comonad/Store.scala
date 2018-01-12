package learnfp.comonad

import learnfp.functor.Maybe._
import learnfp.functor.MaybeInstance._

case class Store[A, S](pos:A, peek:A => S)

object Store {
  implicit def storeCoMonadInstance[S] = new CoMonad[({type E[X] = Store[X, S]})#E] {
    override def counit[A](a: Store[A, S]): A = a.pos
    override def coFlatMap[A, B](a: Store[A, S])(fx: Store[A, S] => B): Store[B, S] = Store[B, S](fx(a), {x:B =>
      a.peek(a.pos)
    })
  }
}