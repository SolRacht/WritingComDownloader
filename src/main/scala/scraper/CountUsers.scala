package scraper

import java.io.{File, FileWriter}
import java.time.format.DateTimeFormatter

import scraper.scraping.Scraper

object CountUsers {
  def apply(scraper: Scraper, config: Config): Unit = {

    val counts = scraper.getOnlineUserCounts.mkString(",")

    val file = new File(config.userCountFilePath)
    if (!file.exists()) {
      file.createNewFile()
    }

    val time =
      DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(java.time.LocalDateTime.now)

    val writer = new FileWriter(file, true)
    writer.write(s"$time, $counts\n")
    writer.close()
  }
}
