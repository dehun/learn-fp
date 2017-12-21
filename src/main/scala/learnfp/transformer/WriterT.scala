package learnfp.transformer


import learnfp.functor.{Functor, Writer}
import learnfp.functor.FunctorOps._
import learnfp.monad.Monad
import learnfp.monad.MonadOps._
import learnfp.monoid.Monoid
import learnfp.monoid.MonoidOps._

import learnfp.functor.WriterInstance._
import learnfp.monad.WriterInstance._

case class WriterT[A, M[_], W](runWriterT:M[Writer[W, A]])(implicit f:Functor[M], m:Monad[M], w:Monoid[W])

object WriterT {
  implicit def writerTFunctorInstance[W, M[_]](implicit f:Functor[M], m:Monad[M], w:Monoid[W]) =
    new Functor[({type E[X] = WriterT[X, M, W]})#E] {
      override def fmap[A, B](a: WriterT[A, M, W])(fx: A => B): WriterT[B, M, W] = WriterT {
        a.runWriterT fmap { av:Writer[W, A] =>
          av fmap {avv => fx(avv)}
        }
      }
    }

  // TODO: to functor ops
  implicit def writerTMonadInstance[W, M[_]](implicit f:Functor[M], m:Monad[M], w:Monoid[W]) =
    new Monad[({type E[X] = WriterT[X, M, W]})#E]() {
      override def pure[A](a: A): WriterT[A, M, W] = WriterT { m.pure(writerMonadInstance.pure(a)) }
      override def flatMap[A, B](a: WriterT[A, M, W])(fx: A => WriterT[B, M, W]): WriterT[B, M, W] = WriterT {
        a.runWriterT >>= {
          av:Writer[W, A] =>
            val (aw, avv) = av.run()
            fx(avv).runWriterT fmap { bw =>
              val (bww, bv) = bw.run()
              Writer[W, B] {() => (bw.monoid.mappend(aw, bww), bv)} }
        }
      }
    }

  def lift[A, W, M[_]](am:M[A])(implicit f:Functor[M], m:Monad[M], w:Monoid[W]):WriterT[A, M, W] = WriterT {
    am fmap { a => writerMonadInstance[W].pure(a)}
  }

  // TODO: to monad ops
  // TODO: lift
  // TODO: pure
  // TODO: tell
}
