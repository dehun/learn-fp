package learnfp.functor

case class Id[A](value:A)

object IdInstance {
  implicit val idInstance:Functor[Id] = new Functor[Id] {
    override def fmap[A, B](a: Id[A])(fx: A => B): Id[B] = Id(fx(a.value))
  }
}
