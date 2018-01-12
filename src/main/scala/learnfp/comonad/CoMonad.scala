package learnfp.comonad

trait CoMonad[F[_]] {
  def counit[A](a:F[A]):A
  def coFlatMap[A, B](a:F[A])(fx:F[A] => B):F[B]
}

class CoMonadOps[F[_], A](a:F[A])(implicit coMonad: CoMonad[F]) {
  def counit = coMonad.counit(a)
  def coFlatMap[B](fx:F[A] => B):F[B] = coMonad.coFlatMap(a)(fx)
}

object CoMonadOps {
  implicit def toCoMonadOps[F[_], A](a:F[A])(implicit coMonad: CoMonad[F]) = new CoMonadOps[F, A](a)
}
