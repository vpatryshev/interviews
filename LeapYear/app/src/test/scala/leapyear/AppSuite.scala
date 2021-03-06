/*
 * This Scala Testsuite was generated by the Gradle 'init' task.
 */
package leapyear

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner
import App._

@RunWith(classOf[JUnitRunner])
class AppSuite extends AnyFunSuite {

  def sampleData =
    """Lions 3, Snakes 3
      |Tarantulas 1, FC Awesome 0
      |Lions 1, FC Awesome 1
      |Tarantulas 3, Snakes 1
      |Nobody 5, Somebody -5
      |Kramer 100, Kramer 100
      |Lions 4, Grouches 0""".stripMargin.split("\n").iterator

  val parsedGames =
    ("Lions",      1) ::
    ("Snakes",     1) ::
    ("Tarantulas", 3) ::
    ("FC Awesome", 0) ::
    ("Lions",      1) ::
    ("FC Awesome", 1) ::
    ("Tarantulas", 3) ::
    ("Snakes",     0) ::
    ("Lions",      3) ::
    ("Grouches",   0) :: Nil
    
  val summary = Map(
    "Lions"      -> 5,
    "Snakes"     -> 1,
    "Tarantulas" -> 6,
    "FC Awesome" -> 1,
    "Grouches"   -> 0)
  
  val sortedScores =
    (6, "Tarantulas"::Nil) ::
    (5, "Lions"::Nil) ::
    (1, "FC Awesome"::"Snakes"::Nil) ::
    (0, "Grouches"::Nil) :: Nil
    
  val scoresWithPositions = List(
    Row("Tarantulas", 6, 1),
    Row("Lions", 5, 2),
    Row("FC Awesome", 1, 3),
    Row("Snakes", 1, 3),
    Row("Grouches", 0, 5)
  )
    
  val expectedData = List(
    "1. Tarantulas, 6 pts",
    "2. Lions, 5 pts",
    "3. FC Awesome, 1 pt",
    "3. Snakes, 1 pt",
    "5. Grouches, 0 pts")

  test("Parsing") {
    val sut = parseLines(sampleData)
    assert(sut === parsedGames)
  }

  test("SummaryScores") {
    val sut = summaryScores(sampleData)
    assert(sut === summary)

  }

  test("SortedByScores") {
    val sut = sortedByScore(sampleData)
    assert(sut === sortedScores)
  }

  test("Positioned") {
    val sut = assignPositions(sortedScores)
    assert(sut == scoresWithPositions)
  }
  
  test("plural") {
    assert(plural("thing", 0) === "0 things")
    assert(plural("thing", 1) === "1 thing")
    assert(plural("Stasi", 2) === "2 Stasis")
    assert(plural("lorem ipsum", 3) === "3 lorem ipsums")
    assert(plural("wolf", 41) === "41 wolfs") // todo: work on NLP
  }

  test("handle") {
    assert(handle(sampleData) == expectedData)
  }
}
