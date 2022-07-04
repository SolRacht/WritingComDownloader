package scraper.scraping

import java.util.UUID

import org.jsoup.nodes.Document
import org.jsoup.{Connection, Jsoup}
import scraper.Config

class Browser {
  val config: Config = Config.get

  implicit class ConnnectionDecorator(connection: Connection) {
    def setHeaders(): Connection = {
      connection
        .cookie("my_session", config.my_session)
        .cookie("user_ntoken", config.user_ntoken)
        .cookie("cpuid", UUID.randomUUID().toString)
        .cookie("__stripe_mid", UUID.randomUUID().toString)
        .cookie("__stripe_sid", UUID.randomUUID().toString)
        .header(
          "Accept",
          "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3"
        )
        .header("Accept-Encoding", "gzip, deflate, br")
        .header("Cache-Control", "no-store, max-age=0")
        .header("pragma", "no-cache")
        .userAgent(
          "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36"
        )
    }
  }

  var lastDocument: Document = _

  def get(url: String): Document = {
    lastDocument = getFromBase("main/interact/item_id/" + url)
    lastDocument
  }

  def getFromBase(url: String): Document = {
    lastDocument = Jsoup
      .connect("http://www.writing.com/" + url)
      .setHeaders()
      .get()
    lastDocument
  }
}
