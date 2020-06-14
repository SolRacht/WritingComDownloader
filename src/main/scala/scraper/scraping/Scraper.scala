package scraper.scraping

import java.time.Instant
import java.util.Date

import org.jsoup.nodes.Document
import org.jsoup.select.Elements

import scala.jdk.CollectionConverters._
import scala.util.{Failure, Success, Try}

class Scraper() {
  implicit class ImplicitDocument(doc: Document) {

    def selects(paths: Seq[String], failureName: String): Elements = {
      var res:Elements = null

      for (p <- paths) {
        if (res == null) {
          Try(doc.select(p)) match {
            case Success(s) =>
              if (!s.isEmpty){
                res = s
              }
            case Failure(_) =>
          }
        }
      }

      if (res == null)
        throw new Exception(s"Could not determine $failureName")
      else
        res
    }
  }

  val browser = new Browser

  def getFaves: Seq[String] = {
    val browser = new Browser
    val doc = browser.getFromBase("main/my_favorites")
    doc.selects(Paths.faves.all, "faves")
      .first()
      .getElementsByAttribute("href")
      .eachAttr("href")
      .asScala
      .toSeq
      .filter(".*view/\\d+".r.matches(_))
      .map("(\\d+)".r.findFirstIn(_).get)
  }

  def getOnlineUserCounts: Seq[String] = {
    val browser = new Browser
    val document = browser.getFromBase("main/current_users.php")

    Seq(
      document.selects(Paths.userCounts.registeredUsers, "wtf").text(),
      document.selects(Paths.userCounts.registeredAuthors, "wtf").text(),
      document.selects(Paths.userCounts.preferredAuthors, "wtf").text(),
      document.selects(Paths.userCounts.moderators, "wtf").text(),
      document.selects(Paths.userCounts.seniorModerators, "wtf").text(),
      document.selects(Paths.userCounts.seniorStaff, "wtf").text(),
      document.selects(Paths.userCounts.privateSessions, "wtf").text(),
      document.selects(Paths.userCounts.totalLoggedIn, "wtf").text(),
      document.selects(Paths.userCounts.guestVisitors, "wtf").text(),
      document.selects(Paths.userCounts.totalSiteUsers, "wtf").text(),
    )
      .map(_.replace(",",""))
  }

  def isRateLimited(doc:Document): Boolean = {
    Try(
      doc.selects(Paths.general.rateLimiter, "Not rate limited!")
        .text
        .startsWith("Just a minute...")
    ) match {
      case Failure(_) =>
        false // not rate limited!
      case Success(_) =>
        true // rate limited!
    }
  }

  def scrapeOutline(doc:Document): Outline = {
    val links = doc.select("pre.norm").select("a[href]")
    val title = doc.selects(Paths.outline.title, "story title (does this story still exist?)").text
    val trueLink = doc.selects(Paths.outline.title, "story title").attr("href").split("/").last

    Outline(
      links = links.eachAttr("href").asScala.toSeq,
      title = title,
      trueLink = trueLink
    )
  }

  def getOutline(itemId: String): Outline = {
    val doc = browser.get(itemId + "/action/outline")
    if (isRateLimited(doc)) {
      throw new RateLimitedException
    }
    scrapeOutline(doc)
  }

  def hasChoices(doc:Document):Boolean =  {
    Try(
      !doc
        .selects(Paths.chapter.deadEnd, "dead end")
        .text()
        .startsWith("THE END")
    ).getOrElse(true)
  }

  def scrapeChapter(doc:Document, descent:String):Chapter = {
    if (isRateLimited(doc)) {
      throw new RateLimitedException
    }

    val body = doc
      .selects(Paths.chapter.body, "chapter body")
      .html()

    val authorName = Try(strToOpt(doc
      .selects(Paths.chapter.authorName, "author name")
      .text()
    )).getOrElse(None)

    val title = doc
      .selects(Paths.chapter.title, "chapter title")
      .text

    val choices =
      if (hasChoices(doc)) {
        doc
          .selects(Paths.chapter.choices, "chapter choices")
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
      authorName,
      Date.from(Instant.now),
      descent
    )
  }

  def getChapter(itemId: String, path:String): Chapter = {
    val doc = browser.get(itemId + "/map/" + path)
    scrapeChapter(doc, path)
  }

  private def strToOpt(s:String): Option[String] = {
    if (s.isBlank)
      None
    else
      Some(s)
  }
}

