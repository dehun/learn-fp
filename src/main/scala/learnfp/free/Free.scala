package learnfp.free

import learnfp.monad.{Monad, MonadOps}
import learnfp.functor.{Functor, FunctorOps}

import scala.annotation.tailrec

sealed trait Free[F[_], A]
final case class Return[F[_], A](a:A) extends Free[F, A]
final case class FlatMap[F[_], A, B](a:Free[F, A], fx:A => Free[F, B]) extends Free[F, B]
final case class LiftF[F[_], A](fn:F[A]) extends Free[F, A]

abstract class Natural[F[_], G[_]] {
  def transform[A](a:F[A]):G[A]
}

object Free {
  implicit def freeFunctorInstance[F[_]] = new Functor[({type E[X] = Free[F, X]})#E] {
    override def fmap[A, B](a: Free[F, A])(fx: A => B): Free[F, B] = ???
  }

  implicit def freeToFunctorOps[F[_], A](a:Free[F, A]) = new FunctorOps[A, ({type E[X] = Free[F, X]})#E](a)

  implicit def freeMonadInstance[F[_]] = new Monad[({type E[X] = Free[F, X]})#E] {
    override def pure[A](a: A): Free[F, A] = ???
    override def flatMap[A, B](a: Free[F, A])(fx: A => Free[F, B]): Free[F, B] = ???
  }

  implicit def freeToMonadOps[F[_], A](a:Free[F, A]) = new MonadOps[A, ({type E[X] = Free[F, X]})#E](a)

  def liftF[F[_], A](a:F[A]):Free[F, A] = LiftF[F, A](a)

  def foldF[F[_], M[_], A](a:Free[F, A])(trans:Natural[F, M])(implicit f:Functor[M], m:Monad[M]):M[A] = ???
}