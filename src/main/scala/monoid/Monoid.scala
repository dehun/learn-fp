package monoid

trait Monoid[A] {
  def mzero:A
  def mappend(lhs:A, rhs:A):A
}
