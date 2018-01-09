package learnfp.cofunctor

case class Predicate[A](getPredicate:A => Boolean)

object Predicate {
  implicit val predicateCoFunctorInstance = new CoFunctor[Predicate] {
    override def cmap[A, B](b: Predicate[B])(fx: A => B): Predicate[A] = Predicate { a:A =>
      b.getPredicate(fx(a))
    }
  }
}
