package scraper.scraping

import java.time.Instant
import java.util.Date

case class Choice
(
  name: String,
  index: Int // 1-based
)

case class Chapter
(
  title: String,
  body: String,
  choices: Seq[Choice],
  author: Option[String],
  dateCreated: Date = Date.from(Instant.now())
)

case class Outline
(
  links: Seq[String],
  title: String,
  trueLink: String
)

class RateLimitedException extends Exception { }

