package learnfp.applicative

object ListInstance {
  import learnfp.functor.ListInstance._
  implicit def appListInstance = new Applicative[List] {
    override def pure[A](a: A): List[A] = ???
    override def <*>[A, R](fxs: List[A => R])(as: List[A]): List[R] = ???
  }
}
