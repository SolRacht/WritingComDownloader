package scraper

import java.io.{File, FileOutputStream}

import org.apache.commons.text.StringEscapeUtils
import scraper.db.{DB, OutlineChapter}
import scraper.scraping.Chapter

object Render {

  private def getAlreadyRenderedDescents(dir: File): Seq[String] = {
    dir.listFiles.map(_.getName).filter("(\\d)+.html".r.matches).map(_.split("\\.")(0))
  }

  def apply(db:DB, config:Config): Unit = {
    val stories = db.getStories

    stories.zipWithIndex.foreach { case (story, index) =>
      // Remove characters that are illegal for files
      val filename =story.title.trim.replace("""[\\\/\:\"\*\?\<\>\|\.]+""","_")
      val dir = new File(s"${config.renderDir}/$filename")
      dir.mkdirs()
      println(s"Rendering ${index+1}/${stories.size}  [${story.title}] to ${dir.getCanonicalPath}")

      val inDBChapters = db.getStoryOutline(story.id)
      renderOutline(dir, inDBChapters, story.title)
      getChaptersWeNeedToRender(inDBChapters, getAlreadyRenderedDescents(dir)).map { chapter =>
        val dbChapter = db.getChapter(story.id, chapter.descent)
        renderChapter(dir, chapter.descent, dbChapter, story.title, inDBChapters, getAlreadyRenderedDescents(dir))
      }
    }
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

  private def renderChapter(dir: File, descent:String, chapter: Chapter, storyTitle: String, dbChapters: Seq[OutlineChapter], alreadyRenderedDescents: Seq[String]) = {
    val file = new File(dir, s"$descent.html")
    file.delete()
    file.createNewFile()
    val html = chapterToHtml(descent, chapter, storyTitle, dbChapters)
    new FileOutputStream(file).write(StringEscapeUtils.unescapeHtml4(html.toString).getBytes)
    println(s"Wrote [$storyTitle]:$descent")
  }

  private def chapterToHtml(descent: String, chapter: Chapter, storyTitle: String, dbChapters: Seq[OutlineChapter]) = {
    val html =
      <html>
        <header>
          <h1>
            {storyTitle}
          </h1>
          <h2>
            {chapter.title}
          </h2>
          <h5>
            By
            {chapter.author.getOrElse("[unknown]")}
          </h5>
        </header>
        <body>
          {chapter.body}<br/>
          <br/>
          <ol>
            {chapter.choices.sortBy(_.index).map { choice =>
            val choiceDescent = descent + choice.index
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
