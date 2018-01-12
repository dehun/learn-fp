package learnfp.cofunctor

case class Show[A](show:A => String)

object Show {
  implicit val showCoFunctorInstance = new CoFunctor[Show] {
    override def cmap[A, B](b: Show[B])(fx: A => B): Show[A] = Show { a:A => b.show(fx(a)) }
  }
}
