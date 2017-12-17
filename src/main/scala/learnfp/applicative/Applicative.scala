package learnfp.applicative

import learnfp.functor.Functor

abstract class Applicative[A, R, F[_]](implicit f:Functor[F]) {
  def <*>(fx:F[A => R])(a:F[A]):F[R]
  def pure[A](a:A):F[A]
}

class FxApplicativeOps[A, R, F[_]](fx:F[A => R]) {
  def <*>(a:F[A])(implicit applicative: Applicative[A, R, F]):F[R] = applicative.<*>(fx)(a)
}

object ApplicativeOps {
  implicit def fxToApplicativeOps[A, R, F[_]](fx:F[A => R])(implicit applicative:Applicative[A, R, F]) = new FxApplicativeOps[A, R, F](fx)
}