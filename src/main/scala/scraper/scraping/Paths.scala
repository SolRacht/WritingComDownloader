package scraper.scraping

/* Because the HTML can vary wildly from chapter to chapter, we try multiple CSS selectors to find certain sections
 */

object Paths {

  object userCounts {
    val registeredUsers: Seq[String] = Seq(
      "div.shadowBox:nth-child(17) > div:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > b:nth-child(1)"
    )
    val registeredAuthors: Seq[String] = Seq(
      "div.shadowBox:nth-child(17) > div:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(2) > td:nth-child(2) > b:nth-child(1)"
    )
    val preferredAuthors: Seq[String] = Seq(
      "div.shadowBox:nth-child(17) > div:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(2) > b:nth-child(1)"
    )
    val moderators: Seq[String] = Seq(
      "div.shadowBox:nth-child(17) > div:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(4) > td:nth-child(2) > b:nth-child(1)"
    )
    val seniorModerators: Seq[String] = Seq(
      "div.shadowBox:nth-child(17) > div:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(5) > td:nth-child(2) > b:nth-child(1)"
    )
    val seniorStaff: Seq[String] = Seq(
      "div.shadowBox:nth-child(17) > div:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(6) > td:nth-child(2) > b:nth-child(1)"
    )
    val privateSessions: Seq[String] = Seq(
      "div.shadowBox:nth-child(17) > div:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(7) > td:nth-child(2) > b:nth-child(1)"
    )
    val totalLoggedIn: Seq[String] = Seq(
      "div.shadowBox:nth-child(17) > div:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(8) > td:nth-child(2) > b:nth-child(1)"
    )
    val guestVisitors: Seq[String] = Seq(
      "div.shadowBox:nth-child(17) > div:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(9) > td:nth-child(2) > b:nth-child(1)"
    )
    val totalSiteUsers: Seq[String] = Seq(
      "div.shadowBox:nth-child(17) > div:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(10) > td:nth-child(2) > big:nth-child(1) > b:nth-child(1)"
    )
  }

  object general {
    val rateLimiter: Seq[String] = Seq(
      "body > div:nth-child(1) > div:nth-child(1) > div:nth-child(1)"
    )
  }

  object outline {
    val title: Seq[String] = Seq(
      ".proll",
      "#Content_Column_Inner > div.shadowBox.shadowBoxTop > table > tbody > tr > td:nth-child(2) > div:nth-child(2) > a"
    )
  }

  // TODO: save the intro too
  object introduction {
    val intro: Seq[String] = Seq(
      "div.shadowBox:nth-child(8) > div:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1)"
    )
  }

  object chapter {
    val body: Seq[String] = Seq(
      "td[id^='chapter_content'] > div[id^='temp'] > div[style='padding:25px 5px 5px 10px;min-width:482px;'] > div",
      "#Content_Column_Inner > div:nth-child(7) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(3) > div:nth-child(1) > div:nth-child(1)",
      "#Content_Column_Inner > div:nth-child(7) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > span:nth-child(1)",
      "#Content_Column_Inner > div:nth-child(7) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > span:nth-child(1)",
      "#Content_Column_Inner > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(3) > div:nth-child(1)",
      "#Content_Column_Inner > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(2) > div:nth-child(1)",
      "#Content_Column_Inner > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(3) > div:nth-child(1)",
      "#Content_Column_Inner > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > span:nth-child(1)",
      "#Content_Column_Inside > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(3) > div:nth-child(1) > div:nth-child(1)",
      "#Content_Column_Inside > div:nth-child(9) > table > tbody > tr > td:nth-child(1) > div > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > div:nth-child(2) > div:nth-child(3) > div:nth-child(1) > div",
      "#Content_Column_Inside > div:nth-child(9) > table > tbody > tr > td:nth-child(1) > div > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > div:nth-child(2) > div:nth-child(2) > div > div > span",
      "#Content_Column_Inside > div:nth-child(9) > table > tbody > tr > td:nth-child(1) > div > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > div:nth-child(2) > div:nth-child(2) > div > div > span",
      "#Content_Column_Inside > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(3) > div:nth-child(1)",
      "#Content_Column_Inside > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(2) > div:nth-child(1)",
      "#Content_Column_Inside > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(2) > span:nth-child(1) > h2:nth-child(1)",
      "#Content_Column_Inside > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1)",
      ".x2222"
    )

    val authorName: Seq[String] = Seq(
      "span.noselect:nth-child(4)",
      "#Content_Column_Inner > div:nth-child(8) > table > tbody > tr > td:nth-child(1) > div > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > div:nth-child(2) > div:nth-child(1) > span.noselect > a",
      "#Content_Column_Inner > div:nth-child(8) > table > tbody > tr > td:nth-child(1) > div > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > div:nth-child(2) > div:nth-child(2) > span.noselect > a"
    )

    val contentId: Seq[String] = Seq(
      "#Content_Column_Inner > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(2) > div:nth-child(1)",
      "KonaBody"
    )

    val title: Seq[String] = Seq(
      "[id^=temp] > div:nth-child(1) > div:nth-child(2) > h2:nth-child(1)",
      "[id^=temp] > div:nth-child(2) > div:nth-child(2) > h2:nth-child(1)",
      "#Content_Column_Inner > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(2) > span:nth-child(1) > h2:nth-child(1)",
      "#Content_Column_Inner > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(1) > span:nth-child(1) > h2:nth-child(1)",
      "#Content_Column_Inner > div:nth-child(7) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(2) > span:nth-child(1) > span:nth-child(1)",
      "#Content_Column_Inner > div:nth-child(7) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(1) > span:nth-child(1) > span:nth-child(1) > span:nth-child(1)",
      "#Content_Column_Inner > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(2) > span:nth-child(1) > span:nth-child(1)",
      "#Content_Column_Inner > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(1) > span:nth-child(1) > span:nth-child(1)",
      "#Content_Column_Inner > div:nth-child(8) > table > tbody > tr > td:nth-child(1) > div > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > div:nth-child(2) > div:nth-child(1) > span:nth-child(1) > big > big > b",
      "#Content_Column_Inner > div:nth-child(8) > table > tbody > tr > td:nth-child(1) > div > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > div:nth-child(2) > div:nth-child(2) > span > big > big > b",
      "#Content_Column_Inner > div:nth-child(8) > table > tbody > tr > td:nth-child(1) > div > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > div:nth-child(2) > div:nth-child(2) > span:nth-child(1) > big > big > b",
      "#Content_Column_Inside > div:nth-child(9) > table > tbody > tr > td:nth-child(1) > div > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > div:nth-child(2) > div:nth-child(1) > span:nth-child(1) > h2",
      "#Content_Column_Inside > div:nth-child(9) > table > tbody > tr > td > div > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > div:nth-child(2) > div:nth-child(2) > span:nth-child(1) > h2",
      "#Content_Column_Inside > div:nth-child(9) > table > tbody > tr > td:nth-child(1) > div > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > div:nth-child(2) > div:nth-child(2) > span:nth-child(1) > h2",
      "#Content_Column_Inside > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(1) > span:nth-child(1) > h2:nth-child(1)",
      "#Content_Column_Inside > div:nth-child(9) > table > tbody > tr > td:nth-child(1) > div > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > div:nth-child(2) > div:nth-child(2) > span:nth-child(1) > h2",
      "#Content_Column_Inside > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(2) > span:nth-child(1) > h2:nth-child(1)"
    )

    val choices: Seq[String] = Seq(
      "[id^=choices] > div:nth-child(1)",
      "#Content_Column_Inside > div:nth-child(9) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(2) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > p:nth-child(2) > i:nth-child(1) > b:nth-child(1) > big:nth-child(1)",
      "#Content_Column_Inner > div:nth-child(7) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > nobr:nth-child(7) > div:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > div:nth-child(1)",
      "#Content_Column_Inner > div:nth-child(7) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(7) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1)",
      "#Content_Column_Inner > div:nth-child(7) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(6) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > div:nth-child(1)",
      "#Content_Column_Inner > div:nth-child(7) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > nobr:nth-child(6) > div:nth-child(3) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > div:nth-child(1)",
      "#Content_Column_Inner > div:nth-child(7) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > nobr:nth-child(6) > div:nth-child(3) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > p:nth-child(1)",
      "#Content_Column_Inner > div:nth-child(7) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > nobr:nth-child(6) > div:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > div:nth-child(1)",
      "#Content_Column_Inner > div:nth-child(7) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > nobr:nth-child(6) > div:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) >#choices_918355 > div:nth-child(1)r:nth-child(1) > td:nth-child(1) > div:nth-child(1) > div:nth-child(1)",
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
      "#Content_Column_Inner > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(6) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > div:nth-child(1)",
      "#Content_Column_Inner > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(3) > div:nth-child(1) > div:nth-child(4) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > div:nth-child(1)",
      "#Content_Column_Inside > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(3) > div:nth-child(4) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > div:nth-child(1)",
      "#Content_Column_Inside > div:nth-child(9) > table > tbody > tr > td:nth-child(1) > div > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > div:nth-child(2) > div:nth-child(5) > table > tbody > tr > td > div > div:nth-child(1)",
      "#Content_Column_Inside > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(5) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > div:nth-child(1)",
      "#Content_Column_Inside > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(6) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > div:nth-child(1)",
      "#Content_Column_Inside > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(2) > div:nth-child(4) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > div:nth-child(1)",
      "#Content_Column_Inside > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > p:nth-child(2)"
    )

    val chapterUrlRegex = ".*/map/.*"

    val deadEnd: Seq[String] = Seq(
      "#Content_Column_Inner > div:nth-child(8) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > p:nth-child(2)"
    )
  }

  object faves {
    val all: Seq[String] = Seq(
      ".menuNode > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1)"
    )
  }
}
