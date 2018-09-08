package learnfp.nested

import learnfp.functor.Functor
import learnfp.applicative.Applicative
import learnfp.traversable.TraversableInstances._

import learnfp.applicative.ApplicativeOps._
import learnfp.functor.FunctorOps._


case class Nested[F[_], G[_], A](value:F[G[A]])

object Nested {
  implicit def functorInstance[F[_], G[_], A](implicit fF: Functor[F], fG: Functor[G]) =
    new Functor[({type E[X] = Nested[F, G, X]})#E] {
      override def fmap[A, B](a: Nested[F, G, A])(fx: A => B): Nested[F, G, B] = Nested[F, G, B] {
        fF.fmap(a.value) { xg => fG.fmap(xg) { x => fx(x) } }
      }
    }

  implicit def applicativeInstance[F[_], G[_], A](implicit fA:Applicative[F], gA:Applicative[G],
                                                  fF:Functor[F], gF:Functor[G],
                                                  ) =
    new Applicative[({type E[X] = Nested[F, G, X]})#E] {
      override def pure[A](a: A): Nested[F, G, A] = Nested(fA.pure(gA.pure(a)))
      override def <*>[A, R](nfx: Nested[F, G, A => R])(na: Nested[F, G, A]): Nested[F, G, R] = {
        lazy val fxfg:F[G[A=>R]] = nfx.value
        lazy val afg:F[G[A]] = na.value
        Nested({ (fx:G[A=>R], a:G[A]) => fx <*> a }.curried `<$>` fxfg `<*>` afg)
      }
  }

  /* We can not nest monads, we should use monad transformers to achieve nesting */
}


