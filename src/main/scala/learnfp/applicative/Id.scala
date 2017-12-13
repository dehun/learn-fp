package learnfp.applicative

import learnfp.functor.{Id, IdInstance => IdFunctorInstance}

object IdInstance {
  import IdFunctorInstance._
  implicit def idApplicativeInstance[A, R] = new Applicative[A, R, Id] {
    override def pure[A](a: A): Id[A] = Id(a)
    override def <*>(fx: Id[A => R])(a: Id[A]): Id[R] = {
      Id(fx.value(a.value))
    }
  }
}
