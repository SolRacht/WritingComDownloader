package scraper.db

import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date

import scalikejdbc._
import scraper.scraping.{Chapter, Choice}

import scala.util.{Failure, Success, Try}

case class Story(id:String, title:String)
case class OutlineChapter(name:String, descent:String)
case class ChapterDescriptor(storyId: String, chapterId: String)
case class MinimalChapter
(
  storyId: String,
  chapterId: String,
  chapterName: String,
  author: Option[String],
  dateCreated: java.util.Date
)

class DB(dbPath:String = "jdbc:sqlite:/db") {
  GlobalSettings.loggingSQLAndTime = LoggingSQLAndTimeSettings(enabled= false)
  GlobalSettings.loggingConnections = false
  Class.forName("org.sqlite.JDBC")
  ConnectionPool.singleton(dbPath, "", "")

  def tx[A](body: DBSession => A):A = {
    using(scalikejdbc.DB(ConnectionPool.borrow())) { db =>
      db.localTx(body)
    }
  }

  def storyExists(storyId: String): Boolean = {
    tx { implicit s =>
      val exists =
        sql"Select 1 from stories where story_id = $storyId"
          .map(rs => rs.any(1))
          .single()
          .apply()
      exists.isDefined
    }
  }

  def chapterExists(storyId: String, descent: String): Boolean = {
    tx { implicit s =>
      val exists =
        sql"Select 1 from chapters where story_id = $storyId and descent = $descent"
          .map(rs => rs.any(1))
          .single()
          .apply()
      exists.isDefined
    }
  }

  def saveChapter(c: Chapter, storyId: String, descent: String): Int = {
    tx { implicit s =>
      sql"""insert into chapters (
           story_id,
           descent,
           author,
           title,
           body,
           choices_cs,
           date_created
         ) values (
           $storyId,
           $descent,
           ${c.author.map( n => sqls"$n").getOrElse(sqls"NULL")},
           ${c.title},
           ${c.body},
           ${
        if (c.choices.isEmpty) {
          null
        } else {
          c.choices.map(_.name).mkString("#")
        }
      },
           CURRENT_TIMESTAMP
         )
    """
        .update()
        .apply()
    }
  }

  def delStory(storyId: String): Int = {
    tx { implicit s =>
      sql"delete from chapters where story_id = $storyId"
        .update()
        .apply()
    }
  }

  def delChapter(storyId: String, descent: String): Int = {
    tx { implicit s =>
      sql"""delete from chapters
          where story_id = $storyId
          and descent = $descent
    """
        .update()
        .apply()
    }
  }

  def insertStory(name:String, id:String): Int = {
    tx { implicit s =>
      sql"insert into STORIES (story_id, title) values ($id, $name)"
        .update().apply()
    }
  }

  def storyFromRS(rs:WrappedResultSet): Story =
    Story(
      id = rs.string("story_id"),
      title = rs.string("title")
    )

  def getStory(id:String):Story = {
    tx { implicit s =>
      sql"select * from STORIES where story_id = $id"
        .map(storyFromRS)
        .single()
        .apply()
        .getOrElse(throw new Exception(s"Cant get story $id"))
    }
  }

  def getStories:Seq[Story] = {
    tx { implicit s =>
      sql"select * from STORIES"
        .map(storyFromRS)
        .list()
        .apply()
    }
  }

  def getStoryOutline(storyId: String):Seq[OutlineChapter] = {
    tx { implicit s =>
      sql"""select title, descent from CHAPTERS where story_id = $storyId"""
        .map(rs =>
          OutlineChapter(
            rs.string("title"),
            rs.string("descent")
          )
        )
        .list()
        .apply()
    }
  }

  val sqliteTimestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
  def chapterFromRS(rs:WrappedResultSet): Chapter =  {
    Chapter(
      title = rs.string("title"),
      body = rs.string("body"),
      choices = {
        val c = rs.stringOpt("choices_cs")
        c match {
          case None =>
            Seq()
          case Some(sq) =>
            sq.split("#").zipWithIndex.map {
              case (name, idx) =>
                Choice(name, idx + 1)
            }
            .toSeq
        }},
      author = rs.stringOpt("author"),
      dateCreated = Try{sqliteTimestampFormat.parse(rs.string("date_created"))} match {
        case Success(d)=>d
        case Failure(_)=> Date.from(Instant.now)
      },
      descent = rs.string("descent")
    )
  }

  def getChapters(storyId: String, chapterIds: Seq[String]):Seq[Chapter] = {
    tx { implicit s =>
      sql"""select * from CHAPTERS
          where story_id = $storyId
          and descent in ($chapterIds)
         """
        .map(chapterFromRS)
        .list()
        .apply()
    }
  }

  def getChapter(storyId: String, chapterId: String):Chapter = {
    tx { implicit s =>
      sql"""select * from CHAPTERS
          where story_id = $storyId
          and descent = $chapterId
         """
        .map(chapterFromRS)
        .single()
        .apply()
        .getOrElse(throw new Exception(s"no chapter at $storyId, $chapterId"))
    }
  }
}
