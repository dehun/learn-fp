package learnfp.free

import org.scalatest.{Matchers, WordSpecLike}
import learnfp.free.Free._
import learnfp.functor.{Functor, Id, StateInstance}
import learnfp.functor.IdInstance._
import learnfp.monad.IdInstance._

class FreeTest extends WordSpecLike with Matchers {
  "Free" should {
    "work with turtle position with Id" in {
      case class Position(x:Int, y:Int)
      sealed trait Movement[A]
      case class Start(pos:Position) extends Movement[Position]
      case class MoveUp(pos:Position, d:Int) extends Movement[Position]
      case class MoveDown(pos:Position, d:Int) extends Movement[Position]
      case class MoveLeft(pos:Position, d:Int) extends Movement[Position]
      case class MoveRight(pos:Position, d:Int) extends Movement[Position]

      class MovementToId extends Natural[Movement, Id] {
        override def transform[A](a: Movement[A]): Id[A] = a match {
          case Start(pos:Position) => Id(pos)
          case MoveUp(p, d) => Id(Position(x=p.x, y=p.y + d))
          case MoveDown(p, d) => Id(Position(x=p.y, y =p.y - d))
          case MoveLeft(p, d) => Id(Position(x=p.x - d, y=p.y))
          case MoveRight(p, d) => Id(Position(x=p.x + d, y=p.y))
        }
      }

      def start(p:Position) = Free.liftF(Start(p))
      def up(p:Position, d:Int) = Free.liftF(MoveUp(p, d))
      def down(p:Position, d:Int) = Free.liftF(MoveDown(p, d))
      def left(p:Position, d:Int) = Free.liftF(MoveLeft(p, d))
      def right(p:Position, d:Int) = Free.liftF(MoveRight(p, d))


      lazy val program = for {
        start <- start(Position(10, 10))
        np1 <- up(start, 2)
        np2 <- right(np1, 2)
        np3 <- right(np2, 1)
      } yield np3
      Free.foldF(program)(new MovementToId()) shouldBe Id(Position(x=10 + 2 + 1, y=10 + 2))
    }

    import learnfp.functor.State
    import learnfp.functor.StateInstance.stateInstance
    import learnfp.monad.StateInstance.stateMonadInstance
    "work with turtle position with State" in {
      case class Position(x:Int, y:Int)
      sealed trait Movement[A]
      case class Start(pos:Position) extends Movement[Unit]
      case class MoveUp(d:Int) extends Movement[Unit]
      case class MoveDown(d:Int) extends Movement[Unit]
      case class MoveLeft(d:Int) extends Movement[Unit]
      case class MoveRight(d:Int) extends Movement[Unit]

      class MovementToState extends Natural[Movement, ({type E[X] = State[Position, X]})#E] {
        override def transform[A](a: Movement[A]): State[Position, A] = a match {
          case Start(pos) => State.put(pos)
          case MoveUp(d) => State.modify[Position](p => p.copy(y=p.y + d))
          case MoveDown(d) => State.modify[Position](p => p.copy(y=p.y - d))
          case MoveLeft(d) => State.modify[Position](p => p.copy(x=p.x - d))
          case MoveRight(d) => State.modify[Position](p => p.copy(x=p.x + d))
        }
      }

      def start(p:Position) = Free.liftF(Start(p))
      def up(d:Int) = Free.liftF(MoveUp(d))
      def down(d:Int) = Free.liftF(MoveDown(d))
      def left(d:Int) = Free.liftF(MoveLeft(d))
      def right(d:Int) = Free.liftF(MoveRight(d))


      val program = for {
        start <- start(Position(10, 10))
        _ <- up(2)
        _ <- right(2)
        end <- right(1)
      } yield end

      Free.foldF[Movement, ({type E[X] = State[Position, X]})#E, Unit](program)(new MovementToState()).run(Position(5, 5)) shouldBe
        (Position(x=10 + 2 + 1, y=10 + 2), ())

      // use same program but now log movements
      import learnfp.transformer.WriterT
      import learnfp.transformer.WriterT._
      import learnfp.monoid.ListMonoid._

      type TurtleState[X] = State[Position, X]
      type TurtleStateWriter[X] = WriterT[X, TurtleState, List[String]]

      // TODO: implement me
      class MovementToWriterTState extends Natural[Movement, TurtleStateWriter] {
        override def transform[A](a: Movement[A]): TurtleStateWriter[A] = a match {
          case Start(pos) => ???
          case MoveUp(d) => ???
          case MoveDown(d) => ???
          case MoveLeft(d) => ???
          case MoveRight(d) => ???
        }
      }

      Free.foldF(program)(new MovementToWriterTState).runWriterT().run(Position(5,5)) shouldBe
        (Position(x=10 + 2 + 1, y= 10 + 2),
          (List("starting at Position(10,10)", "moving up 2 steps", "moving right 2 steps", "moving right 1 steps"), ()))
    }
  }
}
