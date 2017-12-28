package learnfp.free

import learnfp.monad.{Monad, MonadOps}
import learnfp.functor.{Functor, FunctorOps}

import scala.annotation.tailrec

sealed trait Free[F[_], A]
final case class Return[F[_], A](a:A)extends Free[F, A]
final case class FlatMap[F[_], A, B](a:Free[F, A], fx:A => Free[F, B]) extends Free[F, B]
final case class LiftF[F[_], A](fn:F[A]) extends Free[F, A]

abstract class Natural[F[_], G[_]] {
  def transform[A](a:F[A])(implicit f:Functor[G]):G[A]
}

object Free {
  implicit def freeFunctorInstance[F[_]] = new Functor[({type E[X] = Free[F, X]})#E] {
    override def fmap[A, B](a: Free[F, A])(fx: A => B): Free[F, B] = FlatMap(a, {av:A => Return(fx(av))})
  }

  implicit def freeToFunctorOps[F[_], A](a:Free[F, A]) = new FunctorOps[A, ({type E[X] = Free[F, X]})#E](a)

  implicit def freeMonadInstance[F[_]] = new Monad[({type E[X] = Free[F, X]})#E] {
    override def pure[A](a: A): Free[F, A] = Return(a)
    override def flatMap[A, B](a: Free[F, A])(fx: A => Free[F, B]): Free[F, B] = FlatMap(a, fx)
  }

  implicit def freeToMonadOps[F[_], A](a:Free[F, A]) = new MonadOps[A, ({type E[X] = Free[F, X]})#E](a)

  def liftF[F[_], A](a:F[A]):Free[F, A] = LiftF[F, A](a)

  def foldF[F[_], M[_], A](a:Free[F, A])(trans:Natural[F, M])(implicit f:Functor[M], m:Monad[M]):M[A] = a match {
    case Return(av) => m.pure(av)
    case LiftF(fn) => trans.transform(fn)
    case FlatMap(Return(av), fx) => foldF(fx(av))(trans)(f, m)
    case FlatMap(LiftF(av), fx) => m.flatMap(trans.transform(av)) { avv => foldF(fx(avv))(trans)(f, m) }
    case FlatMap(FlatMap(av:Free[F, A], fx), gx) => foldF(FlatMap[F, A, A](av, {avv:A => FlatMap(fx(avv), gx)}))(trans)(f, m)
  }
}