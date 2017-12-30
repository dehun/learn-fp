package learnfp.applicative

import learnfp.functor.Writer
import learnfp.functor.WriterInstance._
import learnfp.monoid.Monoid
import learnfp.monoid.MonoidOps._

object WriterInstance {
  implicit def writerApplicativeInstance[A, R, W](implicit monoid:Monoid[W]) = new Applicative[A, R, ({type E[X] = Writer[W, X]})#E] {
    override def pure[A](a: A): Writer[W, A] = ???
    override def <*>(fx: Writer[W, A => R])(a: Writer[W, A]): Writer[W, R] = ???
  }

  implicit def writerToApplicativeOps[A, R, W](fx:Writer[W, A => R])(implicit monoid:Monoid[W]) =
    new FxApplicativeOps[A, R, ({type E[X] = Writer[W, X]})#E](fx)

  class WriterPureOps[A](a:A) {
    def pure[W](implicit monoid: Monoid[W]) = writerApplicativeInstance[Unit, A, W].pure(a)
  }
  implicit def toPureOps[A](a:A) = new WriterPureOps[A](a)
}
