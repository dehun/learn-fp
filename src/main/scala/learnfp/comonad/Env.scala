package learnfp.comonad

import learnfp.functor.{Functor, FunctorOps}

case class Env[A, R](a:A, r:R)

object Env {
  implicit def envFunctorInstance[R] = new Functor[({type E[X] = Env[X, R]})#E] {
    override def fmap[A, B](a: Env[A, R])(fx: A => B): Env[B, R] = ???
  }

  implicit def envFunctorToFunctorOps[A, R](a:Env[A, R]) = new FunctorOps[A, ({type E[X] = Env[X, R]})#E](a)

  implicit def envCoMonadInstance[R] = new CoMonad[({type E[X] = Env[X, R]})#E] {
    override def counit[A](a: Env[A, R]): A = ???
    override def coFlatMap[A, B](a: Env[A, R])(fx: Env[A, R] => B): Env[B, R] = ???
  }

  implicit def envToCoMonadOps[A, R](a:Env[A, R]) = new CoMonadOps[({type E[X] = Env[X, R]})#E, A](a)
}
