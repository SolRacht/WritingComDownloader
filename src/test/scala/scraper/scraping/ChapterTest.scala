package scraper.scraping

import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}
import scraper.db.DB

class ChapterTest extends FlatSpec with Matchers with BeforeAndAfterAll {
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
    val getFirst = db.getChapter(testStoryId, "1")
    assert(getFirst.author === ch.author)
    assert(getFirst.body === ch.body)
    assert(getFirst.choices === ch.choices)
    assert(getFirst.title === ch.title)


    assert(db.chapterExists(testStoryId, "1"))

    val ch2 = Chapter("foo", "bar", Seq(), None)

    db.saveChapter(ch2, testStoryId, "12")
    val getSecond = db.getChapter(testStoryId, "12")
    assert(getSecond.author === ch2.author)
    assert(getSecond.body === ch2.body)
    assert(getSecond.choices.toSet === ch2.choices.toSet)
    assert(getSecond.title === ch2.title)
  }
}
