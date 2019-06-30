package scraper.scraping

import org.jsoup.nodes.Document
import org.jsoup.select.Elements

import scala.collection.JavaConverters._
import scala.util.{Failure, Success, Try}


class Scraper() {
  implicit class ImplicitDocument(b: Document) {
    def selects(paths: Seq[String], failureName: String): Elements = {
      var res:Elements = null

      // This is dumb, but so am I.
      // I originally wrote this as a super cool foldLeft... except it didn't work
      for (p <- paths) {
        if (res == null) {
          Try(b.select(p)) match {
            case Success(s) =>
              if (!s.isEmpty){
                res = s
              }
            case Failure(_) =>
          }
        }
      }

      if (res == null)
        throw new Exception(s"Failed all paths during $failureName")
      else
        res
    }
  }

  val browser = new Browser

  def scrapeOutline(doc:Document): Outline = {
    val links = doc.select("pre.norm").select("a[href]")
    val title = doc.selects(Paths.outline.title, "title").text

    Outline(
      links = links.eachAttr("href").asScala.toSeq,
      title = title
    )
  }

  def getOutline(itemId: String): Outline = {
    val doc = browser.get(itemId + "/action/outline")
    scrapeOutline(doc)
  }

  def hasChoices(doc:Document):Boolean =  {
    Try(
      !doc
        .selects(Paths.chapter.deadEnd, "deadEnd")
        .text()
        .startsWith("THE END")
    ).getOrElse(true)
  }

  def scrapeChapter(doc:Document):Chapter = {
    val body = doc
      .selects(Paths.chapter.body, "body")
      .html()

    val authorName = Try(strToOpt(doc
      .selects(Paths.chapter.authorName, "authorName")
      .text()
    )).getOrElse(None)

    val title = doc
      .selects(Paths.chapter.title, "title")
      .text

    val choices =
      if (hasChoices(doc)) {
        doc
          .selects(Paths.chapter.choices, "choices")
          .first
          .getElementsByAttribute("href")
          .asScala
          .map(_.text)
          .zipWithIndex
          .map { case (t, idx) =>
            Choice(t, idx)
          }
      } else {
        Seq()
      }

    Chapter(
      title,
      body,
      choices.toSeq,
      authorName
    )
  }

  def getChapter(itemId: String, path:String): Chapter = {
    val doc = browser.get(itemId + "/map/" + path)
    scrapeChapter(doc)
  }

  private def strToOpt(s:String): Option[String] = {
    if (s.isBlank)
      None
    else
      Some(s)
  }
}

