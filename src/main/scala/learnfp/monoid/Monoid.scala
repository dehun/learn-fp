package learnfp.monoid

trait Monoid[A] {
  def mzero:A
  def mappend(lhs:A, rhs:A):A
}

object Monoid {
  def mzero[A](implicit monoid:Monoid[A]):A = monoid.mzero
}

class MonoidOps[A](lhs:A)(implicit monoid:Monoid[A]) {
  def |+|(rhs:A):A = monoid.mappend(lhs, rhs)
}

object MonoidOps {
  implicit def toMonoidOps[A](x:A)(implicit monoid:Monoid[A]):MonoidOps[A] = new MonoidOps[A](x)
}
