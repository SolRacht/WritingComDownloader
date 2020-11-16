package scraper.scraping

import java.io.{File, PrintWriter}
import java.time.Instant

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class ScraperTest extends AnyFlatSpec with Matchers {

  behavior of "scraper"

  val scraper = new Scraper

  def saveDoc(): Unit = {
    new PrintWriter(new File(s"failed_doc_${Instant.now().toString}.html"))
      .write(scraper.browser.lastDocument.toString)
  }

  def test(itemId:String, path:String)(test: Chapter => Unit): Unit = {
    try {
      val chapter = scraper.getChapter(itemId, path)
      test(chapter)
    } catch  {
      case e: Exception =>
        saveDoc()
        throw e
    }
  }

//  it should "identify a rate-limited chapter" in {
//    val doc = scraper.browser.get("1373411-Tiny-Life-Guard/map/1")
//    assert(scraper.isRateLimited(doc) === true)
//  }

  it should "scrape first chapter" in {
    test("1373411-Tiny-Life-Guard", "1") { chapter =>
      assert(chapter.title.equals("You got the Job!!!"))
      assert(chapter.body.startsWith("<div> <span> You stare up at a huge beautiful blonde"))
      assert(chapter.author.nonEmpty)
      assert(chapter.author === Some("someone"))
      assert(chapter.choices.length === 5, chapter.choices)
      assert(chapter.choices(0).name.startsWith("Karen call Juli down"), chapter.choices)
      assert(chapter.choices(1).name.startsWith("Get outta my office and find Juli!!!"), chapter.choices)
      assert(chapter.choices(2).name.startsWith("I will give you a tour"), chapter.choices)
      assert(chapter.choices(3).name.startsWith("Kim train him"), chapter.choices)
      assert(chapter.choices(4).name.startsWith("Go take a self tour"), chapter.choices)
    }
  }

  it should "scrape normal chapter" in {
    test("1373411-Tiny-Life-Guard", "112222111122") { chapter =>
      assert(chapter.title.equals("Juli to the rescue?"))
      assert(chapter.body.startsWith("<span>You hear Roxy"))
      assert(chapter.author.nonEmpty)
      assert(chapter.author === Some("rocky4mayor"))
      assert(chapter.choices.length === 2, chapter.choices)
      assert(chapter.choices(0).name.startsWith("Kim was telling the truth, Jim is under Juli."), chapter.choices)
      assert(chapter.choices(1).name.startsWith("Kim is lying, Jim is still flatten to her butt."), chapter.choices)
    }
  }

  it should "scrape another normal chapter" in {
    test("2188228-Shrink-as-you-want-REWARDS", "1343221") { chapter =>
      assert(chapter.title.equals("Jump and cling on."))
      assert(chapter.body.startsWith("<span>You decided to"))
      assert(chapter.author.nonEmpty)
      assert(chapter.author === Some("Mc Writer"))
      assert(chapter.choices.length === 3, chapter.choices)
      assert(chapter.choices(0).name.startsWith("In between the"), chapter.choices)
      assert(chapter.choices(1).name.startsWith("Inside her Bag."), chapter.choices)
      assert(chapter.choices(2).name.startsWith("Going to a"), chapter.choices)
    }
  }

  it should "scrape a missing author chapter" in {
    test("1373411-Tiny-Life-Guard", "1151111112221211") { chapter: Chapter =>
      assert(chapter.title.equals("The Foreign Girl's Tan"))
      assert(chapter.body.startsWith("<span>Elsa was tired"))
      assert(chapter.author.isEmpty)
      assert(chapter.choices.length === 2, chapter.choices)
      assert(chapter.choices(0).name.contains("She sees you, and manages to peel you off."), chapter.choices)
      assert(chapter.choices(1).name.contains("You have tanned with Elsa! She can't see you!"), chapter.choices)
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
        assert(chapter.choices.head.name.startsWith("You listen"))
    }
  }

  it should "scrape another messed up chapter" in {
    test("1530602-Pass-the-Write-Baton", "11111") {
      chapter =>
        assert(chapter.body.startsWith("<span> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Do you see that little"))
    }
  }

  it should "fetch a story's links from the outline page" in {
    val outline =
      try {
        scraper.getOutline("1826427-Tiny-Brother-VS-Big-Sister")
      } catch{
        case e: Throwable =>
          saveDoc()
          throw e
      }
    assert(outline.links.size >= 109)
    outline.links.foreach { s =>
      assert(s.matches(Paths.chapter.chapterUrlRegex))
    }
    assert(outline.title === "Tiny Brother VS Big Sister")
  }

  it should "fetch a story's true item id from the outline page" in {
    val outline =
      try {
        scraper.getOutline("1826427")
      } catch{
        case e: Throwable =>
          saveDoc()
          throw e
      }
    assert(outline.trueLink === "1826427-Tiny-Brother-VS-Big-Sister")
  }

  it should "fetch a story's title" in {
    val outline = scraper.getOutline("2137802-The-Shrinking-Virus")
    assert(outline.title === "The Shrinking Virus")
  }

  it should "return a list of fave story IDs from the faves list" in {
    val faves = scraper.getFaves
    assert(faves.size > 100)
    // Too bad it's not the full id with the name
    assert(faves.contains("1907651"))
    assert(faves.contains("2200726"))
  }
}
