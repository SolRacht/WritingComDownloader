package scraper.html

import java.io.{File, FileOutputStream}

import org.apache.commons.text.StringEscapeUtils
import scraper.db.{DB, OutlineChapter}
import scraper.scraping.Chapter

/*
 * Very basic HTML rendering for saved stories. Quick and dirty and ugly.
 */

object Renderer {
  private def renderOutline(dir: File, chapters: Seq[OutlineChapter], title:String) = {
    val file = new File(dir, "outline.html")
    // Delete old one
    file.delete()
    file.createNewFile()
    val ostream = new FileOutputStream(file)

    val html =
      <html>
        <header>
          <h1>
            {title}
          </h1>
        </header>
        <body>
          <ul>
            {
            chapters.sortBy(_.descent).map { c =>
              <li>
                <a href={c.descent + ".html"}>
                  {c.descent + " #" + {c.descent.length} + ": " + c.name}
                </a>
              </li>
            }
            }
          </ul>
        </body>
      </html>

    ostream.write(html.toString.getBytes)
  }

  private def renderChapter(dir: File, descent:String, chapter: Chapter, storyTitle: String, existingChapters: Seq[OutlineChapter]) = {

    val file = new File(dir, s"$descent.html")
    // Need to update existing chapters, if a choice now exists.
    file.delete()
    file.createNewFile()
    val ostream = new FileOutputStream(file)

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
            By {chapter.author.getOrElse("[unknown]")}
          </h5>
        </header>
        <body>
          {chapter.body}
          <br/>
          <br/>
          <ol>
            {chapter.choices.sortBy(_.index).map {choice =>
            val choiceDescent = descent + choice.index
            val exists = existingChapters.exists(_.descent == choiceDescent)
            <li>
              {
              if (exists) {
                <a href={choiceDescent + ".html"}>{choice.name}</a>
              } else {
                choice.name
              }
              }
            </li>
          }}
          </ol>
        </body>
      </html>
    ostream.write(StringEscapeUtils.unescapeHtml4(html.toString).getBytes)
  }


  def render(db:DB): Unit = {
    val stories = db.getStories

    stories.zipWithIndex.foreach { case (story, index) =>
      println(s"Rendering ${index+1}/${stories.size}  [${story.title}]")
      val dir = new File(s"stories/${story.title}")
      dir.mkdirs()

      val outlineChapters = db.getStoryOutline(story.id)

      renderOutline(dir, outlineChapters, story.title)
      outlineChapters.foreach { outlineChapter =>
        val chapter = db.getChapter(story.id, outlineChapter.descent)
        renderChapter(dir, outlineChapter.descent, chapter, story.title, outlineChapters)
      }
    }
  }
}
