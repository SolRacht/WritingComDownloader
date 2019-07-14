package scraper.scraping

/* Because the HTML can vary wildly from chapter to chapter, we try mulitple CSS selectors to find certain sections while scraping.
 */

object Paths {

  object outline {
    val title: Seq[String] = Seq(
      ".proll",
      "#Content_Column_Inner > div.shadowBox.shadowBoxTop > table > tbody > tr > td:nth-child(2) > div:nth-child(2) > a"
    ).distinct
  }

  object chapter {
    val body: Seq[String] = Seq(
      "#Content_Column_Inner > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(3) > div:nth-child(1)",
      "#Content_Column_Inner > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(2) > div:nth-child(1)",
      "#Content_Column_Inner > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(3) > div:nth-child(1)"
    ).distinct

    val authorName: Seq[String] = Seq(
      "#Content_Column_Inner > div:nth-child(8) > table > tbody > tr > td:nth-child(1) > div > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > div:nth-child(2) > div:nth-child(1) > span.noselect > a",
      "#Content_Column_Inner > div:nth-child(8) > table > tbody > tr > td:nth-child(1) > div > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > div:nth-child(2) > div:nth-child(2) > span.noselect > a"
    ).distinct

    val authorUrl: Seq[String] = Seq(
      "#Content_Column_Inner > div:nth-child(8) > table > tbody > tr > td:nth-child(1) > div > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > div:nth-child(2) > div:nth-child(2) > a:nth-child(9)",
      "#Content_Column_Inner > div:nth-child(8) > table > tbody > tr > td:nth-child(1) > div > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > div:nth-child(2) > div:nth-child(1) > a:nth-child(9)"
    ).distinct

    val contentId: Seq[String] = Seq(
      "#Content_Column_Inner > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(2) > div:nth-child(1)",
      "KonaBody"
    ).distinct

    val title: Seq[String] = Seq(
      "#Content_Column_Inner > div:nth-child(8) > table > tbody > tr > td:nth-child(1) > div > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > div:nth-child(2) > div:nth-child(1) > span:nth-child(1) > big > big > b",
      "#Content_Column_Inner > div:nth-child(8) > table > tbody > tr > td:nth-child(1) > div > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > div:nth-child(2) > div:nth-child(2) > span > big > big > b",
      "#Content_Column_Inner > div:nth-child(8) > table > tbody > tr > td:nth-child(1) > div > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > div:nth-child(2) > div:nth-child(2) > span:nth-child(1) > big > big > b"
    ).distinct

    val authorLink: Seq[String] = Seq(
      "#Content_Column_Inner > div:nth-child(8) > table > tbody > tr > td:nth-child(1) > div > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > div:nth-child(2) > div:nth-child(2) > span > big > big > b",
      "#Content_Column_Inner > div:nth-child(8) > table > tbody > tr > td:nth-child(1) > div > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > div:nth-child(2) > div:nth-child(2) > span:nth-child(1) > big > big > b",
      "#Content_Column_Inner > div:nth-child(8) > table > tbody > tr > td:nth-child(1) > div > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > div:nth-child(2) > div:nth-child(1) > a:nth-child(9)"
    ).distinct

    val choices: Seq[String] = Seq (
      "#Content_Column_Inner > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(6) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > div:nth-child(1)",
      "#Content_Column_Inner > div:nth-child(8) > table > tbody > tr > td:nth-child(1) > div > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > div:nth-child(2) > div:nth-child(4) > table > tbody > tr > td > div:nth-child(1) > div:nth-child(1)",
      "#Content_Column_Inner > div:nth-child(8) > table > tbody > tr > td:nth-child(1) > div > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > div:nth-child(2) > div:nth-child(6) > table > tbody > tr > td > div:nth-child(1) > div:nth-child(1)",
      "#Content_Column_Inner > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(6) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > div:nth-child(1)",
      "#Content_Column_Inner > div:nth-child(8) > table > tbody > tr > td:nth-child(1) > div > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > div:nth-child(2) > div:nth-child(5) > table > tbody > tr > td > div > div:nth-child(1)",
      "#Content_Column_Inner > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(3) > div:nth-child(4) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > div:nth-child(1)",
      "#Content_Column_Inner > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(6) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > div:nth-child(1)",
      ".KonaBody > div:nth-child(26) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > div:nth-child(1)",
      ".KonaBody > div:nth-child(34) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > div:nth-child(1)",
      "#Content_Column_Inner > div:nth-child(7) > table > tbody > tr > td:nth-child(1) > div > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td.norm > div > div:nth-child(5) > table > tbody > tr > td > div > div:nth-child(1)",
      "#Content_Column_Inner > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(6) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > div:nth-child(1)",
      "#Content_Column_Inner > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(3) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > div:nth-child(1)",
      "#Content_Column_Inner > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(3) > div:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > div:nth-child(1)",
      "#Content_Column_Inner > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(6) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > div:nth-child(1)"
    ).distinct

    val chapterUrlRegex = ".*/map/.*"

    val deadEnd: Seq[String] = Seq(
      "#Content_Column_Inner > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > p:nth-child(2)"
    ).distinct
  }
}
