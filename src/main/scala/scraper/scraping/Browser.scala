package scraper.scraping

import org.jsoup.nodes.Document
import org.jsoup.{Connection, Jsoup}
import scraper.Config

class Browser {
  val config: Config = Config.get

  implicit class ConnnectionDecorator(connection:Connection) {
    def setHeaders: Connection = {
      connection
        .cookie("my_session", config.my_session)
        .cookie("user_token", config.user_token)
        .cookie("username", config.username)
        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
        .header("Accept-Encoding", "gzip, deflate, br")
        .header("Accept-Encoding", "gzip, deflate, br")
        .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
    }
  }

  def get(url:String): Document = {
    Jsoup
      .connect("http://www.writing.com/main/interact/item_id/" + url)
      .setHeaders
      .get
  }
}

