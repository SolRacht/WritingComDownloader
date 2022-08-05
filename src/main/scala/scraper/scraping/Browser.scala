package scraper.scraping

import java.util.UUID

import org.jsoup.nodes.Document
import org.jsoup.{Connection, Jsoup}
import scraper.Config
import scala.util.Random

class Browser {
  val config: Config = Config.get
  val random = new Random()

  implicit class ConnnectionDecorator(connection: Connection) {
    def setHeaders(): Connection = {
      connection
        .cookie("my_session", config.my_session)
        .cookie("user_ntoken", config.user_ntoken)
        .cookie("cpuid", UUID.randomUUID().toString.filterNot(_ == '-'))
        .cookie("__stripe_mid", UUID.randomUUID().toString)
        .cookie("__stripe_sid", UUID.randomUUID().toString)
        .header(
          "Accept",
          "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")
        .header("Accept-Language","en-US,en;q=0.5")
        .header("Accept-Encoding", "gzip, deflate, br")
        .header("Cache-Control", "no-store, max-age=0")
        .header("pragma", "no-cache")
        .userAgent("Mozilla/5.0 (X11; Linux x86_64; rv:103.0) Gecko/20100101 Firefox/103.0")
    }
  }

  def sleepRandomIfSlowMode():Unit = {
    if (config.slowMode) {
      Thread.sleep(1000 * (5 + random.nextInt(5)))
    }
  }

  var lastDocument: Document = _

  def get(url: String): Document = {
    sleepRandomIfSlowMode()
    lastDocument = getFromBase("main/interact/item_id/" + url)
    lastDocument
  }

  def getFromBase(url: String): Document = {
    sleepRandomIfSlowMode()
    lastDocument = Jsoup
      .connect("http://www.writing.com/" + url)
      .setHeaders()
      .get()
    lastDocument
  }
}
