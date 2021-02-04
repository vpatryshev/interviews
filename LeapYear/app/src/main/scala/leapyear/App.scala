package leapyear

import java.io.File
import scala.Console.err
import scala.io.Source
import scala.language.postfixOps
import scala.util.matching.Regex

object App {
  def main(args: Array[String]): Unit = {
    val in: Source = try {
      if (args.isEmpty) {
        Source.fromInputStream(System.in)
      }
      else {
        val file = new File(args(0))
        if (file.isAbsolute) Source.fromFile(file) else 
        Source.fromFile(s"../${args(0)}") // this is a hack, works only in this gradle project
      }
    } catch {
      case x: Exception =>
        err.println(x.getMessage)
        System.exit(1)
        throw x // Nothing as a result
    }
    
    println(handle(in.getLines()) mkString "\n")
  }

  val EntryFormat: Regex = "(.*) (\\d+)$".r
  
  def parse(line: String): List[(String, Int)] = try {
    val found = line split "," collect {
      case EntryFormat(name, score) => (name.trim, score.toInt)
    } toList

    if (found.length == 2 && found(0)._1 != found(1)._1) {
      val d = found(0)._2 - found(1)._2
      if (d < 0) {
        (found(0)._1, 0)::(found(1)_1, 3)::Nil
      } else if (d > 0) {
        (found(0)._1, 3)::(found(1)_1, 0)::Nil
      } else {
        (found(0)._1, 1)::(found(1)_1, 1)::Nil
      }
    } else Nil
  }
  catch {
    case x: Exception => Nil
  }

  def parseLines(input: Iterator[String]): List[(String, Int)] =
    input.toList map parse filter (_.length == 2) flatten 
  
  def summaryScores(input: Iterator[String]): Map[String, Int] = {
    val groupedByName = parseLines(input).groupBy(_._1)

    groupedByName map {
      case (name, gameScores) => name -> gameScores.map(_._2).sum
    }
  }
  
  def sortedByScore(input: Iterator[String]): List[(Int, List[String])] = {
    val groupedByScore: Map[Int, List[String]] = summaryScores(input).groupBy(_._2) map {
      case (score, map) => score -> map.keys.toList.sorted
    }

    groupedByScore.toList.sortBy(-_._1)
  }

  def assignPosition(pos: Int, points: Int, names: List[String]): (Int, List[Row]) = {
    (pos + names.length, names map (Row(_, points, pos)))
  }

  def assignPositions(input: List[(Int, List[String])]): List[Row] = (
    input.foldLeft ((1, List[Row]())) {
      case ((pos, ys), (points, names)) =>
        val (newPos, newRows) = assignPosition(pos, points, names)
        (newPos, ys++newRows)
    })._2

  def handle(input: Iterator[String]): Seq[String] = {
    
    val sorted: List[(Int, List[String])] = sortedByScore(input)

    val positioned: List[Row] = assignPositions(sorted)

    val result: List[String] = positioned map (_.toString)
    
    result
  }

  def plural(word: String, n: Int): String = s"$n $word" + (if(n == 1) "" else "s")

  case class Row(name: String, points: Int, position: Int) {
    override def toString =  s"$position. $name, ${plural("pt", points)}"
  }

}
