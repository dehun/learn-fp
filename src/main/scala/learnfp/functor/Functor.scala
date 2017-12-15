package learnfp.functor

trait Functor[F[_]] {
  def fmap[A, B](a:F[A])(fx:A => B):F[B]
}

class FunctorOps[A, F[_]](a:F[A])(implicit functor:Functor[F]) {
  def fmap[B](fx:A => B):F[B] = functor.fmap(a)(fx)
  def map[B](fx:A => B):F[B] = functor.fmap(a)(fx) // to be compatible with for
}

class FxFunctorOps[A, B](fx:A => B) {
  def `<$>`[F[_]](a:F[A])(implicit functor:Functor[F]):F[B] = {
    functor.fmap(a)(fx)
  }
}

object FunctorOps {
  implicit def toFunctorOps[A, F[_]](f:F[A])(implicit functor:Functor[F]):FunctorOps[A, F] = new FunctorOps(f)
  implicit def fxToFunctorOps[A, B](fx:A => B):FxFunctorOps[A, B] = new FxFunctorOps[A, B](fx)
}


