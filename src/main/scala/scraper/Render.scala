package scraper

import java.io.{File, FileOutputStream}

import org.apache.commons.text.StringEscapeUtils
import scraper.db.{DB, OutlineChapter}
import scraper.scraping.Chapter

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration


object Render {

  private def getAlreadyRenderedDescents(dir: File): Seq[String] = {
    dir.listFiles.map(_.getName).filter("(\\d)+.html".r.matches).toIndexedSeq.map(_.split("\\.")(0))
  }

  def apply(db:DB, config:Config): Unit = {
    val stories = db.getStories
    val futures = db.getStories.zipWithIndex.map { case (story, index) =>
      Future {
        // Remove characters that are illegal for files
        val filename = story.title.trim.replaceAll("""[\\\/\:\"\*\?\<\>\|\.]+""","_")
        val dir = new File(s"${config.renderDir}/$filename")
        dir.mkdirs()

        println(s"Rendering ${index+1}/${stories.size}  [${story.title}] to ${dir.getCanonicalPath}")

        val inDBChapters = db.getStoryOutline(story.id)

        val toRenderChapterIds = getChaptersWeNeedToRender(inDBChapters, getAlreadyRenderedDescents(dir)).map(_.descent)

        if (toRenderChapterIds.nonEmpty) {
          renderOutline(dir, inDBChapters, story.title)
          db.getChapters(story.id,toRenderChapterIds).map { chapter =>
            renderChapter(dir, chapter, story.title, inDBChapters)
          }
        }
      }
    }
    Await.result(Future.sequence(futures),Duration.Inf)
  }

  private def renderOutline(dir: File, chapters: Seq[OutlineChapter], title:String) = {
    val file = new File(dir, "outline.html")
    // Delete old one
    file.delete()
    file.createNewFile()
    val ostream = new FileOutputStream(file)

    val html = outlineToHtml(chapters, title)

    ostream.write(html.toString.getBytes)
  }

  private def outlineToHtml(chapters: Seq[OutlineChapter], title: String) = {
    val html =
      <html>
        <header>
          <h1>
            {title}
          </h1>
        </header>
        <body>
          <ul>
            {chapters.sortBy(_.descent).map { c =>
            <li>
              <a href={c.descent + ".html"}>
                {c.descent + " #" + {
                c.descent.length
              } + ": " + c.name}
              </a>
            </li>
          }}
          </ul>
        </body>
      </html>
    html
  }

  private def getChaptersWeNeedToRender(inDBChapters:Seq[OutlineChapter], alreadyRenderedDescents: Seq[String]):Seq[OutlineChapter] = {
    val newChapters = inDBChapters.filter(d => !alreadyRenderedDescents.contains(d.descent))
    val newChapterParentDescents = newChapters.map(_.descent.dropRight(1)).distinct
    val parentsOfNewChapters = inDBChapters.filter(d => newChapterParentDescents.contains(d.descent))
    newChapters ++ parentsOfNewChapters
  }

  private def renderChapter(dir: File, chapter: Chapter, storyTitle: String, dbChapters: Seq[OutlineChapter]) = {
    val file = new File(dir, s"${chapter.descent}.html")
    file.delete()
    file.createNewFile()
    val html = chapterToHtml(chapter, storyTitle, dbChapters)
    new FileOutputStream(file).write(StringEscapeUtils.unescapeHtml4(html.toString).getBytes)
    println(s"Wrote [$storyTitle]:${chapter.descent}")
  }

  private def chapterToHtml(chapter: Chapter, storyTitle: String, dbChapters: Seq[OutlineChapter]) = {
    val html =
      <html>
        <header>
          <h1>
            {storyTitle}
          </h1>
          <h3>
            {chapter.descent.length}: {chapter.title}
          </h3>
          <h5>
            By {chapter.author.getOrElse("[unknown]")}
          </h5>
          <a href={"outline.html"}>
            Outline
          </a>
          { if (chapter.descent.length > 1) {
            <br/>
            <a href={chapter.descent.dropRight(1) + ".html"}>
              Previous chapter
            </a>
          }}
        </header>
        <br/>
        <body>
          {chapter.body}<br/>
          <br/>
          <ol>
            {chapter.choices.sortBy(_.index).map { choice =>
            val choiceDescent = chapter.descent + choice.index
            val exists = dbChapters.exists(_.descent == choiceDescent)
            <li>
              {if (exists) {
              <a href={choiceDescent + ".html"}>
                {choice.name}
              </a>
            } else {
              choice.name
            }}
            </li>
          }}
          </ol>
        </body>
      </html>
    html
  }

}
