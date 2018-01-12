package learnfp.comonad

case class Env[A, R](a:A, r:R)

object Env {
  implicit def envCoMonadInstance[R] = new CoMonad[({type E[X] = Env[X, R]})#E] {
    override def counit[A](a: Env[A, R]): A = a.a
    override def coFlatMap[A, B](a: Env[A, R])(fx: Env[A, R] => B): Env[B, R] = {
      Env(fx(a), a.r)
    }
  }

  implicit def envToCoMonadOps[A, R](a:Env[A, R]) = new CoMonadOps[({type E[X] = Env[X, R]})#E, A](a)
}
