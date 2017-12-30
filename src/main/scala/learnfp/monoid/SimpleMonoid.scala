package learnfp.monoid

object SimpleMonoid {
  case class Sum(value:Int)
  implicit val sumMonoidInstance:Monoid[Sum] = new Monoid[Sum] {
    override def mzero: Sum = ???
    override def mappend(lhs: Sum, rhs: Sum): Sum = ???
  }

  case class Product(value:Int)
  implicit val productMonoidInstance:Monoid[Product] = new Monoid[Product] {
    override def mzero: Product = ???
    override def mappend(lhs: Product, rhs: Product): Product = ???
  }
}
