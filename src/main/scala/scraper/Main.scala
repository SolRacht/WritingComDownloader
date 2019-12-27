package scraper

import java.util.concurrent.atomic.AtomicBoolean

import akka.actor.{ActorRef, ActorSystem, Props}
import scraper.db.DB
import scraper.actors._

import scala.util.Random
import scraper.scraping.Scraper
import scraper.util.RandomCoffee
import scraper.actors.Commands._
import scraper.html.Renderer

trait MainStuff {
  // The number of Akka actors scraping chapters
  val NUM_CHAPTER_SCRAPERS = 5

  // The number of Akka actors scraping outlines
  val NUM_OUTLINE_SCRAPERS = 5

  private def setupActors(working: AtomicBoolean) = {
    val sys = ActorSystem()

    val workMonitor = sys.actorOf(Props(new WorkMonitor(working)))

    val chapterScrapers: Seq[ActorRef] = (1 to NUM_CHAPTER_SCRAPERS).map { _ =>
      sys.actorOf(Props(new ChapterScraper(workMonitor)))
    }

    val storyScrapers: Seq[ActorRef] = (1 to NUM_OUTLINE_SCRAPERS).map { _ =>
      sys.actorOf(Props(new StoryScraper(chapterScrapers, workMonitor)))
    }
    (workMonitor, storyScrapers)
  }

  def scrapeNewChapters(scraper:Scraper, db:DB, config:Config): Unit = {
    val stillWorking = new AtomicBoolean(true)
    val (workMonitor, storyScrapers) = setupActors(stillWorking)
    val storyIdsToScrape = config.storiesToScrape

    println(s"${storyIdsToScrape.length} stories to check.")

    // Evenly and randomly signal scrapers with story IDs.
    Random.shuffle(storyIdsToScrape)
      .grouped(storyScrapers.size)
      .foreach { ids =>
        ids.zipWithIndex.foreach {
          case (id, idx) =>
            // Signal the monitor before the work begins to avoid a race condition.
            workMonitor ! WORK_ADDED
            // Sleep to stagger the work out a bit.
            Thread.sleep(50)
            storyScrapers(idx) ! SCRAPE_STORY(id)
        }
      }

    // Wait until work finished.
    Thread.sleep(1000)
    while (stillWorking.get) {
      workMonitor ! CHECK_STATUS
      Thread.sleep(1000)
    }
  }

  def render(db:DB) = {
    // Very basic HTML rendering of stories.
    Renderer.render(db)
  }
}

object Main extends App with MainStuff {
  println(s"Your coffee today: $RandomCoffee")

  lazy val config = Config.get
  lazy val db = new DB()
  lazy val scraper = new Scraper

  try {
    args.map(_.toLowerCase).last match {
      case "render" =>
        render(db)
      case "scrape" =>
        scrapeNewChapters(scraper, db, config)
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
