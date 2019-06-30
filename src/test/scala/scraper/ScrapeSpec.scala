package scraper

import org.scalatest._
import scraper.scraping.{Chapter, Paths, Scraper}

class ScrapeSpec extends FlatSpec with Matchers {

  behavior of "scraper"

  val scraper = new Scraper

  def test(itemId:String, path:String)(test: Chapter => Unit): Unit = {
    val chapter = scraper.getChapter(itemId, path)
    test(chapter)
  }

  it should "scrape first chapter" in {
    // First chapter
    test("2147026-Tiny-Tutor", "1") { chapter =>
      assert(chapter.title.equals("Morning Comes"))
      assert(chapter.body.startsWith("You were woken up"))
      assert(chapter.author.nonEmpty)
      assert(chapter.author === Some("superdude9"))
      assert(chapter.choices.length === 5, chapter.choices)
      assert(chapter.choices(0).name.startsWith("To pick a client"), chapter.choices)
      assert(chapter.choices(1).name.startsWith("To take one last day off"), chapter.choices)
      assert(chapter.choices(2).name.startsWith("Before you decide, Stacy"), chapter.choices)
      assert(chapter.choices(3).name.startsWith("Before you decide, Shannon"), chapter.choices)
      assert(chapter.choices(4).name.startsWith("Before you decide, Shannon"), chapter.choices)
    }
  }

  it should "scrape normal chapter" in {
    test("2147026-Tiny-Tutor", "113311121") { chapter =>
      assert(chapter.title.equals("Adult Swim"))
      assert(chapter.body.contains("You know how to swim, Sammy?"))
      assert(chapter.author.nonEmpty)
      assert(chapter.author === Some("TheNumber1"))
      assert(chapter.choices.length === 2, chapter.choices)
      assert(chapter.choices(0).name.contains("continues to play"), chapter.choices)
      assert(chapter.choices(1).name.contains("inside to relax"), chapter.choices)
    }
  }

  it should "scrape a missing author chapter" in {
    test("2147026-Tiny-Tutor", "1433232") { chapter: Chapter =>
      assert(chapter.title.equals("Shannon’s Sweet Find"))
      assert(chapter.body.startsWith("After your eyes adjusted"))
      assert(chapter.author.isEmpty)
      assert(chapter.choices.length === 2, chapter.choices)
      assert(chapter.choices(0).name.contains("scooped up"), chapter.choices)
      assert(chapter.choices(1).name.contains("use the restroom"), chapter.choices)
    }
  }

  it should "scrape a dead-end" in {
    test("1373411-Tiny-Life-Guard", "143111112211212222") { chapter =>
      assert(chapter.title.equals("The Perfect Prison"))
      assert(chapter.body.contains("Jim spent the night in Kims boob"))
      assert(chapter.author.isDefined)
      assert(chapter.author.get === "The Dwarf")
      assert(chapter.choices.length === 0, chapter.choices)
    }
  }

  it should "scrape a messed up chapter" in {
    // not sure what's wrong with this one
    test("1384303-At-Home-and-Shrunken-O", "1353112211212122112112121") {
      chapter =>
        assert(chapter.choices.length === 2)
        assert(chapter.choices.head.name.contains("You listen"))
    }
  }

  it should "fetch a story's links from the outline page" in {
    val outline = scraper.getOutline("1826427-Tiny-Brother-VS-Big-Sister")
    assert(outline.links.size >= 109)
    outline.links.foreach { s =>
      assert(s.matches(Paths.chapter.chapterUrlRegex))
    }
    assert(outline.title === "Tiny Brother VS Big Sister")
  }
}
