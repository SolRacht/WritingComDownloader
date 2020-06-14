package scraper

import java.util.concurrent.atomic.AtomicBoolean

import akka.actor.{ActorRef, ActorSystem, Props}
import scraper.actors.Commands.{CHECK_STATUS, SCRAPE_STORY, WORK_ADDED}
import scraper.actors.{AChapterScraper, AStoryScraper, AWorkMonitor}
import scraper.scraping.Scraper

import scala.util.Random

object Scrape {
  // The number of actors scraping chapters
  val NUM_CHAPTER_SCRAPERS = 5

  // The number of actors scraping outlines
  val NUM_OUTLINE_SCRAPERS = 5

  private def setupActors(working: AtomicBoolean) = {
    val sys = ActorSystem()
    val workMonitor = sys.actorOf(Props(new AWorkMonitor(working)))

    val chapterScrapers: Seq[ActorRef] = (1 to NUM_CHAPTER_SCRAPERS).map { _ =>
      sys.actorOf(Props(new AChapterScraper(workMonitor)))
    }

    val storyScrapers: Seq[ActorRef] = (1 to NUM_OUTLINE_SCRAPERS).map { _ =>
      sys.actorOf(Props(new AStoryScraper(chapterScrapers, workMonitor)))
    }

    (workMonitor, storyScrapers)
  }

  private def getStoryIds(config:Config): Seq[String] = {
    if (config.useConfigStoryList) {
      config.storiesToScrape
    } else {
      new Scraper().getFaves
    }
  }

  def apply(config:Config): Unit = {
    if (config.my_session.isEmpty) {
      println("Configuration `my_session` is blank. Have you entered your writing.com cookies?")
      System.exit(1)
    }
    if (config.user_ntoken.isEmpty) {
      println("Configuration `user_ntoken` is blank. Have you entered your writing.com cookies?")
      System.exit(1)
    }
    val stillWorking = new AtomicBoolean(true)
    val (workMonitor, storyScrapers) = setupActors(stillWorking)
    val storyIds = getStoryIds(config)

    println(s"${storyIds.length} stories to check.")

    // Evenly and randomly signal scrapers with story IDs.
    evenRandomShuffle(storyIds, storyScrapers) {
      case (storyId, scraper) =>
        // Signal the monitor before the work begins to avoid a race condition.
        workMonitor ! WORK_ADDED
        // Sleep to stagger the work out a bit.
        Thread.sleep(10)
        scraper ! SCRAPE_STORY(storyId)
    }
    // Wait until work finished.
    Thread.sleep(1000)
    while (stillWorking.get) {
      workMonitor ! CHECK_STATUS
      Thread.sleep(1000)
    }
  }

  private def evenRandomShuffle[A,B](things: Seq[A], thingDoers: Seq[B])(work: (A, B) => Unit): Unit = {
    Random
      .shuffle(things)
      .grouped(thingDoers.size)
      .foreach { ids =>
        ids.zipWithIndex.foreach {
          case (thing, doerIndex) => work(thing, thingDoers(doerIndex))
        }
      }
  }
}
