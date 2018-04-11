package modules

import java.util.Calendar

import com.google.inject.AbstractModule
import services.{GreetingService, RealGreetingService, RealSandwichService, SandwichService}

class Module extends AbstractModule {

  def configure() = {

    val calendar = Calendar.getInstance()
    bind(classOf[Calendar]).toInstance(calendar)

    bind(classOf[GreetingService]).to(classOf[RealGreetingService])

    bind(classOf[SandwichService]).to(classOf[RealSandwichService])
  }
}
