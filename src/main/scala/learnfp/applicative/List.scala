package learnfp.applicative



object ListInstance {
  import learnfp.functor.ListInstance._
  implicit def appListInstance[A, R] = new Applicative[A, R, List] {
    override def pure[A](a: A): List[A] = List(a)
    override def <*>(fxs: List[A => R])(as: List[A]): List[R] = {
      for {fx <- fxs
           a <- as} yield fx(a)
    }
  }
}
