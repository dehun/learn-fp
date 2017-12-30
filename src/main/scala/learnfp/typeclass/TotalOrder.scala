package learnfp.typeclass

trait TotalOrder[A] {
  def less(lhs:A, rhs:A):Boolean
}

object TotalOrderInstances {
  implicit val intInstance:TotalOrder[Int] = new TotalOrder[Int] {
    override def less(lhs: Int, rhs: Int): Boolean = ???
  }

  implicit val stringInstance:TotalOrder[String] = new TotalOrder[String] {
    override def less(lhs: String, rhs: String): Boolean = ???
  }

  implicit def listInstance[T](implicit suborder:TotalOrder[T]):TotalOrder[List[T]] = new TotalOrder[List[T]] {
    override def less(lhs: List[T], rhs: List[T]): Boolean = ???
  }
}

object Comparator {
  @annotation.implicitNotFound("No instance of TotalOrder found")
  def less[A](lhs:A, rhs:A)(implicit order:TotalOrder[A]) = {
    order.less(lhs, rhs)
  }
}