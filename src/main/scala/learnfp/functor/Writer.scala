package learnfp.functor

import learnfp.monoid.Monoid

case class Writer[W, A](run: () => (W, A))(implicit val monoid:Monoid[W])

object Writer {
  def tell[W](m:W)(implicit monoid:Monoid[W]):Writer[W, Unit] = ???
}

object WriterInstance {
  implicit def writerInstance[W] = new Functor[({type E[X] = Writer[W, X]})#E] {
    override def fmap[A, B](a: Writer[W, A])(fx: A => B): Writer[W, B] = ???
  }

  implicit def writerToFunctorOps[A, W](a:Writer[W, A]) = new FunctorOps[A, ({type E[X] = Writer[W, X]})#E](a)

  class WriterFunctorOps[A, B](fx:A => B) {
    def `<$>`[W](a:Writer[W, A]):Writer[W, B] = a fmap fx
  }
  implicit def writerToFunctorFxOps[A, R](fx:A => R) = new WriterFunctorOps[A, R](fx)
}
