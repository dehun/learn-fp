package learnfp.functor

object Maybe {
  sealed trait Maybe[+A]
  case class Just[A](value: A) extends Maybe[A]
  object Just {
    def fmap[A, B](a: Just[A])(fx: A => B): Just[B] = Just[B](fx(a.value))
  }

  case class Nothing[A]() extends Maybe[A]
  object Nothing {
    def fmap[A, B](a: Nothing[A])(fx: A => B): Nothing[B] = Nothing[B]()
  }

  def nothing[A]():Maybe[A] = Nothing[A]()
  def just[A](x:A):Maybe[A] = Just(x)
}

object MaybeInstance {
  import Maybe._

  implicit val maybeInstance:Functor[Maybe] = new Functor[Maybe] {
    override def fmap[A, B](a: Maybe[A])(fx: A => B): Maybe[B] = ???
  }
}
