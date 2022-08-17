package io.devsun.crawl

import io.devsun.crawl.extractor.HtmlContentExtractor
import io.devsun.recommend.report.{Report, ReportRepository}

import java.time.LocalDate

class ReportCrawlerService(reportRepository: ReportRepository) {
  val crawler = HttpCrawler(new HtmlContentExtractor("<table>", "</table>"))

  def crawl(crawlDate: LocalDate): List[Report] = {
    val queryDate = crawlDate.toString.replaceAll("-", "")
    println("================================")
    println(s" CrawlDate: $crawlDate")
    println("================================")

    val url =
      s"http://comp.fnguide.com/SVO2/ASP/SVD_Report_Summary_Data.asp?fr_dt=$queryDate&to_dt=$queryDate&stext=&check=all&sortOrd=5&sortAD=A&_=1655116815402"
    val items = crawler.crawl(url, "table>tbody>tr")

    items.map { item =>
      val summary = item.getString("(//tr)[1]")
      val description: Option[String] = item.getString("(//dd)")
      val targetPriceStr =
        item.getString("(//td)[4]").getOrElse("0").replaceAll(",", "")

      val targetPrice = if (targetPriceStr.isBlank) {
        BigDecimal(0)
      } else {
        BigDecimal(targetPriceStr)
      }

      summary match {
        case Some(summaryStr) =>
          val splitted: Array[String] = summaryStr.split(" ")
          val date = splitted(0).replaceAll("/", "-")
          var name = splitted(1)
          var code = splitted(2)

          println(date, name, code, targetPrice, description)

          if (!code.startsWith("A")) {
            name = splitted(1) + " " + splitted(2)
            code = splitted(3)
          }

          reportRepository.save(
            Report(
              LocalDate.parse(date),
              code,
              name,
              description.getOrElse(""),
              targetPrice
            )
          )
      }
    }
  }
}
