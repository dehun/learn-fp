package learnfp.traversable

import learnfp.applicative.Applicative
import learnfp.functor.Functor

class TraversableOps[A, F[_]](initialXs:List[F[A]])(implicit functor: Functor[F]) {
  import learnfp.functor.FunctorOps._
  import learnfp.applicative.ApplicativeOps._

  def traverse[B](fx:A => B)(implicit applicative: Applicative[List[B], List[B], F]):F[List[B]] = ???

  def sequence(implicit applicative: Applicative[List[A], List[A], F]):F[List[A]] = ???
}

object TraversableOps {
  implicit def toTraversableOps[A, F[_]](xs:List[F[A]])(implicit functor:Functor[F]) = new TraversableOps[A, F](xs)
}
