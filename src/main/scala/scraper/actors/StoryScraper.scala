package scraper.actors

import java.util.UUID

import akka.actor.{Actor, ActorRef}
import scraper.actors.Commands._
import scraper.db.DB
import scraper.scraping.Scraper

import scala.util.Random

class StoryScraper(chapterScrapers: Seq[ActorRef], WorkMonitor: ActorRef) extends Actor {
  lazy val name: String = UUID.randomUUID().toString.take(4)
  lazy val db = new DB()
  lazy val scraper = new Scraper

  var _nextIndex = -1
  def nextChapterScraperIndex: Int = {
    _nextIndex += 1
    _nextIndex %= chapterScrapers.size
    _nextIndex
  }

  def scrape(id:String): Unit = {
    val outline = scraper.getOutline(id)
    if (!db.storyExists(id)) {
      db.insertStory(outline.title, id)
    }

    val newChapters = outline.links
      .map(_.split("/").last)
      .filter(!db.chapterExists(id, _))

    println(s"[${outline.title}]: ${newChapters.size} chapter(s).")

    newChapters.foreach { descent =>
      WorkMonitor ! WORK_ADDED
      chapterScrapers(nextChapterScraperIndex) ! SCRAPE_CHAPTER(id, descent, outline.title)
      Thread.sleep(10) // Allow other storyscrapers to signal
    }
  }

  def receive: PartialFunction[Any, Unit] = {
    case SCRAPE_STORY(id) =>
      try {
        scrape(id)
      } finally {
        WorkMonitor ! WORK_COMPLETED
      }
  }
}
