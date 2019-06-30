package scraper.actors

import java.util.UUID

import akka.actor.{Actor, ActorRef}
import scraper.actors.Commands._
import scraper.db.DB
import scraper.scraping.Scraper

/* BUG: the fact that Chapter and StoryScraper both have their own DB connections means they can try to write
 *      at the same time. Writers will just throw if they try to write while the db is locked. Not a huge issue.
 */

class ChapterScraper(monitor: ActorRef) extends Actor {
  lazy val name: String = UUID.randomUUID().toString.take(4)
  lazy val db = new DB()
  lazy val scraper = new Scraper

  def scrape(id:String, path:String, title: String): Unit = {
    val chapter = scraper.getChapter(id, path)
    db.saveChapter(chapter, id, path)
    println(s"[$title]: $path")
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
