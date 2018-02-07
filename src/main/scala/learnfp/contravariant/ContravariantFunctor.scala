package learnfp.contravariant

trait ContravariantFunctor[F[_]] {
  def cmap[A, B](b:F[B])(fx:A => B):F[A]
}

class ContravariantFunctorOps[B, F[_]](b:F[B])(implicit contravariantFunctor:ContravariantFunctor[F]) {
  def cmap[A](fx:A => B):F[A] = contravariantFunctor.cmap(b)(fx)
}

object ContravariantFunctorOps {
  implicit def toContravariantFunctorOps[B, F[_]](b:F[B])(implicit contravariantFunctor:ContravariantFunctor[F]) =
    new ContravariantFunctorOps[B, F](b)
}
