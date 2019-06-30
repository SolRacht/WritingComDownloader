package scraper

import com.typesafe.config.ConfigFactory

import scala.collection.JavaConverters._

case class Config
(
  username: String,
  my_session: String,
  user_token: String,
  storiesToScrape: Seq[String],
)

object Config {
  private val config = ConfigFactory.load("application.conf")
  private val conf = Config(
    username = config.getString("username"),
    my_session = config.getString("my_session"),
    user_token = config.getString("user_token"),
    storiesToScrape = config.getStringList("stories").asScala.toSeq.distinct,
  )
  def get: Config = conf
}
