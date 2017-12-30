package learnfp.transformer

import learnfp.functor.{Functor, FunctorOps, State}
import learnfp.functor.FunctorOps._
import learnfp.functor.StateInstance._
import learnfp.monad.{Monad, MonadOps}
import learnfp.monad.MonadOps._
import learnfp.monad.StateInstance._

case class StateT[S, M[_], A](runStateT: S => M[(S, A)])(implicit m:Monad[M])

object StateT {
  implicit def stateTFunctorInstance[S, M[_]](implicit f:Functor[M], m:Monad[M]) = new Functor[({type E[X] = StateT[S, M, X]})#E] {
    override def fmap[A, B](a: StateT[S, M, A])(fx: A => B): StateT[S, M, B] = ???
  }

  implicit def stateTToFunctorOps[S, M[_], A](a:StateT[S, M, A])(implicit f:Functor[M], m:Monad[M]) =
    new FunctorOps[A, ({type E[X] = StateT[S, M, X]})#E](a)

  implicit def stateTMonadInstance[S, M[_]](implicit f:Functor[M], m:Monad[M]) = new Monad[({type E[X] = StateT[S, M, X]})#E] {
    override def pure[A](a: A): StateT[S, M, A] = ???
    override def flatMap[A, B](a: StateT[S, M, A])(fx: A => StateT[S, M, B]): StateT[S, M, B] = ???
  }

  implicit def stateTToMonadOps[S, M[_], A](a:StateT[S, M, A])(implicit f:Functor[M], m:Monad[M]) =
    new MonadOps[A, ({type E[X] = StateT[S, M, X]})#E](a)

  implicit def stateTMonadTransformerInstance[S, M[_]](implicit f:Functor[M], m:Monad[M]) =
    new MonadTransformer[M, ({type E[X, Y[_]] = StateT[S, Y, X]})#E] {
      override def lift[A](a: M[A]): StateT[S, M, A] = ???
    }

  implicit def stateTToMonadTransOps[S, M[_], A](a:M[A])(implicit m:Monad[M]) = new MonadTransOps[A, M](a)

  def lift[S, M[_], A](a:M[A])(implicit f:Functor[M], m:Monad[M]) = stateTMonadTransformerInstance[S, M].lift(a)

  def putT[S, M[_]](s:S)(implicit m:Monad[M]):StateT[S, M, Unit] = ???
  def getT[S, M[_]](implicit m:Monad[M]):StateT[S, M, S] = ???
}

