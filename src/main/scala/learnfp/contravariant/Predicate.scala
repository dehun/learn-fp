package learnfp.contravariant

case class Predicate[A](getPredicate:A => Boolean)

object Predicate {
  implicit val predicateContravariantFunctorInstance = new ContravariantFunctor[Predicate] {
    override def cmap[A, B](b: Predicate[B])(fx: A => B): Predicate[A] = ???
  }
}
