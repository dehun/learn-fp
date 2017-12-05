package learnfp.typeclass

trait TypeClass[A] {
  def foo[A]:String
}

object TypeClassInstances {
  implicit val intInstance:TypeClass[Int] = new TypeClass[Int] {
    override def foo[A]: String = "int"
  }

  implicit val stringInstance:TypeClass[String] = new TypeClass[String] {
    override def foo[A]: String = "string"
  }
}
