package controllers

import java.util.Calendar

import org.scalatestplus.play._
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.FakeRequest
import play.api.test.Helpers._
import services.GreetingService

object FakeMorningGreeter extends GreetingService {
  def greeting: String = {
    val currentHour = FakeMorningCalendar.get(Calendar.HOUR_OF_DAY)
    if (currentHour < 12)
      "Good morning!"
    else
      "Good afternoon!"
  }
}

object FakeAfternoonGreeter extends GreetingService {
  def greeting: String = {
    val currentHour = FakeAfternoonCalendar.get(Calendar.HOUR_OF_DAY)
    if (currentHour < 12)
      "Good morning!"
    else
      "Good afternoon!"
  }
}

class WelcomeControllerSpec extends PlaySpec with GuiceOneAppPerTest {

  "WelcomeController GET" must {

    "return a successful response" in {
      val controller = new WelcomeController(FakeMorningGreeter)
      val result = controller.welcome.apply(FakeRequest())
      status(result) mustBe OK
    }

    "respond to the /welcome URL" in {
      val request = FakeRequest(GET, "/welcome").withHeaders("Host" -> "localhost")
      val home = route(app, request).get
      status(home) mustBe OK
    }

    "return some HTML" in {
      val controller = new WelcomeController(FakeMorningGreeter)
      val result = controller.welcome.apply(FakeRequest())
      contentType(result) mustBe Some("text/html")
    }

    "say good morning and have a title" in {
      val controller = new WelcomeController(FakeMorningGreeter)
      val result = controller.welcome.apply(FakeRequest())
      contentAsString(result) must include("<h1>Good morning!</h1>")
      contentAsString(result) must include("<title>Welcome!</title>")
    }

    "say good afternoon when it's the afternoon and have a title" in {
      val controller = new WelcomeController(FakeAfternoonGreeter)
      val result = controller.welcome.apply(FakeRequest())
      contentAsString(result) must not include ("<h1>Good morning!</h1>")
      contentAsString(result) must include("<h1>Good afternoon!</h1>")
      contentAsString(result) must include("<title>Welcome!</title>")
    }
  }
}

object FakeMorningCalendar extends FakeCalendar {
  override def get(field: Int): Int = 11
}

object FakeAfternoonCalendar extends FakeCalendar {
  override def get(field: Int): Int = 13
}

class FakeCalendar extends java.util.Calendar {
  override def computeTime(): Unit = ???

  override def computeFields(): Unit = ???

  override def add(i: Int, i1: Int): Unit = ???

  override def roll(i: Int, b: Boolean): Unit = ???

  override def getMinimum(i: Int): Int = ???

  override def getMaximum(i: Int): Int = ???

  override def getGreatestMinimum(i: Int): Int = ???

  override def getLeastMaximum(i: Int): Int = ???
}