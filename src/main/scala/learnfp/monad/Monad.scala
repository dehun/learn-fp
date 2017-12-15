package learnfp.monad

import learnfp.functor.Functor

abstract class Monad[M[_]](implicit functor:Functor[M]) {
  def pure[A](a:A):M[A]
  def flatMap[A, B](a:M[A])(fx: A => M[B]):M[B]
}

class MonadOps[A, M[_]](a:M[A])(implicit monad:Monad[M]) {
  def >>=[B](fx:A => M[B]):M[B] = monad.flatMap(a)(fx)
  def flatMap[B](fx: A => M[B]):M[B] = monad.flatMap(a)(fx)
}

class MonadOpsPure[A](a:A) {
  def pure[M[_]](implicit monad:Monad[M]) = monad.pure(a)
}

object MonadOps {
  implicit def toMonadOps[A, M[_]](a:M[A])(implicit monad:Monad[M]) = new MonadOps[A, M](a)
  implicit def toMonadOpsPure[A](a:A) = new MonadOpsPure(a)
}
