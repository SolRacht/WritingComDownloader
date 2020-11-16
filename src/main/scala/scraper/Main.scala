package scraper

import java.time.Instant

import scraper.db.DB
import scraper.scraping.Scraper
import scraper.util.RandomCoffee

object Main extends App {
  println(s"################################")
  println(s"Running at ${Instant.now()}")
  println(s"Your coffee today: $RandomCoffee")

  lazy val config = Config.get
  lazy val db = new DB()
  lazy val scraper = new Scraper

  try {
    args.map(_.toLowerCase).last match {
      case "render" => Render(db, config)
      case "scrape" => Scrape(config)
      case "count"  => CountUsers(scraper, config) // Experimental command
      case _        => println("Unknown command.")
    }
  } catch {
    case e: Throwable =>
      println(s"Exception: ${e.getMessage}")
      e.printStackTrace()
      System.exit(1)
  }

  println("Thank you for playing!")
  System.exit(0)
}
