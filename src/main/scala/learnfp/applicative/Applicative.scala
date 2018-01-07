package learnfp.applicative

import learnfp.functor.Functor

abstract class Applicative[F[_]](implicit functor:Functor[F]) {
  def <*>[A, R](fx:F[A => R])(a:F[A]):F[R]
  def pure[A](a:A):F[A]
}

class FxApplicativeOps[A, R, F[_]](fx:F[A => R]) {
  def <*>(a:F[A])(implicit applicative: Applicative[F]):F[R] = applicative.<*>(fx)(a)
}

object ApplicativeOps {
  implicit def fxToApplicativeOps[A, R, F[_]](fx:F[A => R])(implicit applicative:Applicative[F]) = new FxApplicativeOps[A, R, F](fx)
}