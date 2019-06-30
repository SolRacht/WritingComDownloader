package scraper

import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}
import scraper.db.DB
import scraper.scraping._

class ChapterSpec extends FlatSpec with Matchers with BeforeAndAfterAll {
  val db = new DB()
  val testStoryId = "TEST_TEST"

  behavior of "chapter"

  override def afterAll(): Unit = {
    db.delStory(testStoryId)
  }

  it should "round trip the db" in  {
    val ch = Chapter("foo", "bar", Seq(Choice("foo", 1), Choice("bar", 2)), Some("baz"))

    assert(!db.chapterExists(testStoryId, "1"))
    db.saveChapter(ch, testStoryId, "1")
    assert(db.getChapter(testStoryId, "1") == ch)
    assert(db.chapterExists(testStoryId, "1"))

    val ch2 = Chapter("foo", "bar", Seq(), None)

    db.saveChapter(ch2, testStoryId, "12")
    assert(db.getChapter(testStoryId, "12") == ch2)
  }
}
