package learnfp.cofunctor

trait CoFunctor[F[_]] {
  def cmap[A, B](b:F[B])(fx:A => B):F[A]
}

class CoFunctorOps[B, F[_]](b:F[B])(implicit cofunctor:CoFunctor[F]) {
  def cmap[A](fx:A => B):F[A] = cofunctor.cmap(b)(fx)
}

object CoFunctorOps {
  implicit def toCoFunctorOps[B, F[_]](b:F[B])(implicit cofunctor:CoFunctor[F]) = new CoFunctorOps[B, F](b)
}
