package scraper.scraping

import java.time.Instant
import java.util.Date

import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scraper.db.DB

class ChapterTest extends AnyFlatSpec with Matchers with BeforeAndAfterAll {
  val db = new DB()
  val testStoryId = "TEST_TEST"

  behavior of "chapter"

  override def afterAll(): Unit = {
    db.delStory(testStoryId)
  }

  it should "round trip the db" in  {
    val ch = Chapter("foo", "bar", Seq(Choice("foo", 1), Choice("bar", 2)), Some("baz"), Date.from(Instant.now), "foo")

    assert(!db.chapterExists(testStoryId, "1"))
    db.saveChapter(ch, testStoryId, "1")
    val getFirst = db.getChapter(testStoryId, "1")
    assert(getFirst.author === ch.author)
    assert(getFirst.body === ch.body)
    assert(getFirst.choices === ch.choices)
    assert(getFirst.title === ch.title)


    assert(db.chapterExists(testStoryId, "1"))

    val ch2 = Chapter("foo", "bar", Seq(), None, Date.from(Instant.now), "foo")

    db.saveChapter(ch2, testStoryId, "12")
    val getSecond = db.getChapter(testStoryId, "12")
    assert(getSecond.author === ch2.author)
    assert(getSecond.body === ch2.body)
    assert(getSecond.choices.toSet === ch2.choices.toSet)
    assert(getSecond.title === ch2.title)
  }
}
