package learnfp.monad

import learnfp.functor.Writer
import learnfp.functor.WriterInstance._
import learnfp.monoid.Monoid
import learnfp.monoid.MonoidOps._

object WriterInstance {
  implicit def writerMonadInstance[W](implicit monoid:Monoid[W]) = new Monad[({type E[X] = Writer[W, X]})#E] {
    override def pure[A](a: A): Writer[W, A] = Writer { () => (monoid.mzero, a) }
    override def flatMap[A, B](a: Writer[W, A])(fx: A => Writer[W, B]): Writer[W, B] = {
      val (aw, av) = a.run()
      val (fxw, fxv) = fx(av).run()
      Writer {() => (aw |+| fxw, fxv)}
    }
  }

  implicit def writerToMonadOps[W, A](w:Writer[W, A])(implicit monoid:Monoid[W]) = new MonadOps[A, ({type E[X] = Writer[W, X]})#E](w)
}
