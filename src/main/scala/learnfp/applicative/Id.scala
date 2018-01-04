package learnfp.applicative

import learnfp.functor.{Id, IdInstance => IdFunctorInstance}

object IdInstance {
  import IdFunctorInstance._
  implicit val idApplicativeInstance = new Applicative[Id] {
    override def pure[A](a: A): Id[A] = Id(a)
    override def <*>[A, R](fx: Id[A => R])(a: Id[A]): Id[R] = {
      Id(fx.value(a.value))
    }
  }
}
