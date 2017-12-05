package learnfp.typeclass

trait TypeClass[A] {
  def foo(x:A):String
}

object TypeClassInstances {
  implicit val intInstance:TypeClass[Int] = new TypeClass[Int] {
    override def foo(x:Int): String = "int"
  }

  implicit val stringInstance:TypeClass[String] = new TypeClass[String] {
    override def foo(x:String): String = "string"
  }
}

object TypeClassUser {
  def foo[A](x:A)(implicit typeClass:TypeClass[A]):String  = {
    typeClass.foo(x)
  }
}
