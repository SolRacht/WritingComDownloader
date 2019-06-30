package scraper.actors

object Commands {
  // StoryScraper
  case class SCRAPE_STORY(id:String)

  // ChapterScraper
  case class SCRAPE_CHAPTER(id:String, path:String, title: String)

  // WorkMonitor
  case class WORK_ADDED()
  case class WORK_COMPLETED()
  case class CHECK_STATUS()
}
