package learnfp.monad

import learnfp.functor.Id

object IdInstance {
  import learnfp.functor.IdInstance._
  implicit val monadIdInstance = new Monad[Id] {
    override def pure[A](a: A): Id[A] = ???
    override def flatMap[A, B](a: Id[A])(fx: A => Id[B]): Id[B] = ???
  }
}
