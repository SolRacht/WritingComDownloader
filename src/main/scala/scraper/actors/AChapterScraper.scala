package scraper.actors

import java.util.UUID

import akka.actor.{Actor, ActorRef}
import scraper.actors.Commands._
import scraper.db.DB
import scraper.scraping.Scraper

import scala.util.{Failure, Success, Try}

/* BUG: the fact that Chapter and StoryScraper both have their own DB connections means they can try to write
 *      at the same time. Writers will just throw if they try to write while the db is locked. Not a huge issue.
 */

class AChapterScraper(monitor: ActorRef) extends Actor {
  lazy val name: String = UUID.randomUUID().toString.take(4)
  lazy val db = new DB()
  lazy val scraper = new Scraper

  def scrape(id: String, path: String, title: String): Unit = {
    Try {
      scraper.getChapter(id, path)
    } match {
      case Success(chapter) =>
        // There seems to be a race condition I don't understand
        if (!db.chapterExists(id, path)) {
          db.saveChapter(chapter, id, path)
          println(s"Saved [$title: $path]")
        }
      case Failure(exception) =>
        println(s"FAILED! [$id : $path] reason: ${exception.getMessage}")
    }
  }

  def receive: PartialFunction[Any, Unit] = {
    case SCRAPE_CHAPTER(id, path, title) =>
      try {
        scrape(id, path, title)
      } finally {
        monitor ! WORK_COMPLETED
      }
  }
}
