package learnfp.applicative

import learnfp.functor.State
import learnfp.functor.StateInstance._

object StateInstance {
  implicit def stateApplicativeInstance[S, A, R] = new Applicative[A, R, ({type E[X] = State[S, X]})#E] {
    override def pure[A](a: A): State[S, A] = State {s:S => (s, a)}
    override def <*>(fx: State[S, A => R])(a: State[S, A]): State[S, R] = State {s:S =>
      val (fxs, fxv) = fx.run(s)
      val (as, av) = a.run(fxs)
      (as, fxv(av))
    }
  }

  implicit def stateToApplicativeOps[S, A, R](a:State[S, A => R]) = new ApplicativeOps[A, R, ({type E[X] = State[S, X]})#E](a)
}
