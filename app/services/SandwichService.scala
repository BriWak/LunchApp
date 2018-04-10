package services

import com.google.inject.ImplementedBy
import models.Sandwich

@ImplementedBy(classOf[RealSandwichService])
trait SandwichService {
  def sandwiches() : List[Sandwich]
}

class RealSandwichService extends SandwichService {
  val ham = Sandwich("Ham", 1.55, "Very tasty")
  val cheese = Sandwich("Cheese", 2.55, "Cheese tastic")
  val egg = Sandwich("Egg", 1.15, "Fresh")
  override def sandwiches(): List[Sandwich] = List(ham, cheese, egg)
}
