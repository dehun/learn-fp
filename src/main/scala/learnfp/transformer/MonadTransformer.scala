package learnfp.transformer

import learnfp.monad.Monad

abstract class MonadTransformer[M[_], T[_, _[_]]](implicit m:Monad[M], tm:Monad[({type E[X] = T[X, M]})#E]) {
  def lift[A](a:M[A]):T[A, M]
}

class MonadTransOps[A, M[_]](a:M[A])(implicit m:Monad[M]) {
  def lift[T[_, _[_]]](implicit tm:Monad[({type E[X] = T[X, M]})#E], t:MonadTransformer[M, T]):T[A, M] =
    t.lift(a)
}

object MonadTransformer {
  implicit def toMonadTransOps[A, M[_]](a:M[A])(implicit m:Monad[M]) = new MonadTransOps[A, M](a)
}
