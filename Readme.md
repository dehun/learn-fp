# Learn FP #
## Intro ##
This course was created with purpose to better understand functional programming idioms using Scala language.
Material is structured as set of stub/unimplemented functions/classes and tests for them.
Your objective is to make all unit tests green. It is learn-by-doing course.

## Inspiration ##
NICTA course was a great and interesting challenge for me to do in Haskell.
I think Scala community will benefit from the similar course.

## Target audience ##
The material in here is quite poor on theoretical part.
I have tried to link other articles that I have found to be good into this course to compensate for this.
Some prior experience with functional idioms is recommended, but not neccessary.

## Progression ##
It is important to keep the progression - a lot of things depend on each other.
Implementing something = making all tests green for that thing.

### Typeclasses ###

- Observe general typeclass pattern in `learnfp/typeclass/TypeClass.scala`.
- Implement `learnfp/typeclass/TotalOrder.scala`
- Implement `learnfp/typeclass/Show.scala`
- Extra material: TBD

### Monoids ###

- Observe general monoid pattern in `learnfp/monoid/Monoid.scala`
- Implement `learn-fp/src/main/scala/learnfp/monoid/ListMonoid.scala`
- Implement `learn-fp/src/main/scala/learnfp/monoid/SimpleMonoid.scala`
- Implement `learnfp/monoid/PairMonoid.scala`

### Functors ###

- Observe general functor pattern in `learnfp/functor/Functor.scala`
- Implement `learnfp/functor/Id.scala`
- Implement `learnfp/functor/Maybe.scala`
- Implement `learnfp/functor/List.scala`
- Implement `learnfp/functor/Disjunction.scala`
- Implement `learnfp/functor/Writer.scala`
- Implement `learnfp/functor/State.scala`
- Extra material: TBD

### Monads ###

- Observe general monad pattern in `learnfp/monad/Monad.scala`
- Implement `learnfp/monad/Id.scala`
- Implement `learnfp/monad/Maybe.scala`
- Implement `learnfp/monad/List.scala`
- Implement `learnfp/monad/Disjunction.scala`
- Implement `learnfp/monad/Writer.scala`
- Implement `learnfp/monad/State.scala`
- Extra material: TBD

### Applicatives ###

- Observe general applicative pattern in `learnfp/applicative/Applicative.scala`
- Implement `learnfp/applicative/Id.scala`
- Implement `learnfp/applicative/Maybe.scala`
- Implement `learnfp/applicative/List.scala`
- Implement `learnfp/applicative/Disjunction.scala`
- Implement `learnfp/applicative/Writer.scala`
- Implement `learnfp/applicative/State.scala`
- Implement `learnfp/traversable/Traversable.scala`
- Extra material: TBD

### Monad Transformers ###

- Observe general monad transformer typeclass in `learnfp/transformer/MonadTransformer.scala`
- Implement `learnfp/transformer/IdT.scala`
- Implement `learnfp/transformer/MaybeT.scala`
- Implement `learnfp/transformer/WriterT.scala`
- Implement `learnfp/transformer/StateT.scala`
- Extra material: TBD

### Free monad ###

- Implement `learnfp/free/Free.scala` and pass all unit tests in `learn-fp/src/test/scala/learnfp/free/FreeTest.scala`
- Extra material:
  - http://blog.krobinson.me/posts/monads-part-2-the-free-monad/
  - https://underscore.io/blog/posts/2015/04/14/free-monads-are-simple.html
  
# Credits #
- Yuriy Netesov - initial implementation