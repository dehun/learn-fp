package learnfp.transformer

import learnfp.monad.Monad

abstract class MonadTransformer[M[_], T[_, _[_]]](implicit m:Monad[M], t:Monad[({type E[X] = T[X, M]})#E]) {
  def lift[A](a:M[A]):T[A, M]
}
