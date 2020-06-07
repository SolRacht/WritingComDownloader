package scraper

import java.lang.System.Logger

import com.typesafe.config.ConfigFactory

import scala.jdk.CollectionConverters._

case class Config
(
  username: String,
  my_session: String,
  user_ntoken: String,
  storiesToScrape: Seq[String],
  userCountFilePath: String,
  renderDir: String,
  useConfigStoryList: Boolean
)

object Config {
  private val config = ConfigFactory.load("application.conf")
  private val conf = Config(
    renderDir = config.getString("rendered_stories_directory"),
    username = config.getString("username"),
    my_session = config.getString("my_session"),
    user_ntoken = config.getString("user_ntoken"),
    storiesToScrape = config.getStringList("stories").asScala.toSeq.distinct,
    userCountFilePath = config.getString("user_count_file_path"),
    useConfigStoryList = config.getBoolean("use_config_stories")
  )
  def get: Config = conf
}
