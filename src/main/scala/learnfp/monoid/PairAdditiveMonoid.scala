package learnfp.monoid

case class Pair[A, B](a:A, b:B)

object PairAdditiveMonoid {

  implicit def nestedMonoidInstance[A, B](implicit aMonoid:Monoid[A], bMonoid:Monoid[B]):Monoid[Pair[A, B]] =
    new Monoid[Pair[A, B]] {
      override def mzero: Pair[A, B] = ???
      override def mappend(lhs: Pair[A, B], rhs: Pair[A, B]): Pair[A, B] =
        ???
  }
}
