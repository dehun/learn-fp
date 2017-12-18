package learnfp.transformer

import learnfp.functor.{Functor, Id}
import learnfp.monad.Monad

case class IdT[A, F[_]](value:F[Id[A]])

object IdT {
  implicit def idtFunctorInstance[F[_]](implicit outerFunctor:Functor[F]) = new Functor[({type E[X] = IdT[X, F]})#E] {
    override def fmap[A, B](a: IdT[A, F])(fx: A => B): IdT[B, F] = {
      IdT(outerFunctor.fmap(a.value) {x:Id[A] => Id(fx(x.value))})
    }
  }

  implicit def idtMonadInstance[M[_]](implicit outerMonad:Monad[M], outerFunctor:Functor[M]) = new Monad[({type E[X] = IdT[X, M]})#E] {
    override def pure[A](a: A): IdT[A, M] = IdT(outerMonad.pure(Id(a)))
    override def flatMap[A, B](a: IdT[A, M])(fx: A => IdT[B, M]): IdT[B, M] = {
      IdT(outerMonad.flatMap(a.value) { av:Id[A] => fx(av.value).value })
    }
  }
}
