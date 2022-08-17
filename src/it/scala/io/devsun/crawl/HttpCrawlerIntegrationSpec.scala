package io.devsun.crawl

import io.devsun.crawl.analysis.total.TrendItem
import io.devsun.crawl.extractor.XmlContentExtractor
import io.devsun.recommend.report.MongoReportRepository
import io.devsun.recommend.trend.Trend
import org.mongodb.scala.MongoClient
import org.openqa.selenium.By
import org.scalatest.WordSpec
import org.slf4j.LoggerFactory

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class HttpCrawlerIntegrationSpec extends WordSpec {
  val logger = LoggerFactory.getLogger(this.getClass)
  val service = new ReportCrawlerService(
    new MongoReportRepository(
      MongoClient()
    )
  )

  "HttpCrawler" should {
    "crawl xml data via http" in {
      val crawler = HttpCrawler(XmlContentExtractor)
      val url = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchStay?serviceKey=ndnUTHcxjSuzEwUB%2FR8ME20WRMp0NoYhmqmySaJjd%2BEojebYJ6VkFUWwpowkyKX1lvhQAV%2BBdtdp5%2FdEodFQdA%3D%3D&numOfRows=100&pageNo=10&MobileOS=ETC&MobileApp=AppTest&arrange=A&listYN=Y"
      val items = crawler.crawl(url, "item")
      logger.info("{}", items)
    }

    "crawl html data via http" in {
      val startDate = LocalDate.of(2022, 6, 1)
      val endDate = LocalDate.of(2022, 6, 17)

      val diff = ChronoUnit.DAYS.between(startDate, endDate)

      (0L to diff).foreach { d =>
        val nextDate = startDate.plusDays(d)

        service.crawl(nextDate)
      }
    }

    "crawl today" in {
      service.crawl(LocalDate.now())
    }

    "crawl trend" in {
      import org.openqa.selenium.chrome.ChromeDriver

      System.setProperty("webdriver.chrome.driver", "/kakao/programs/chromedriver")
      val driver = new ChromeDriver

      val code = "A035720"

      driver
        .get(s"https://m.finance.daum.net/quotes/$code/influential_investors/foreign")

      Thread.sleep(1000)

      val elements = driver.findElements(By.xpath("//*[@id=\"root\"]/div/main/section/article[3]/div[2]/table/tbody/tr"))

      val items = (0 until elements.size()).map {
        i =>
          val nested = elements.get(i).findElements(By.tagName("td"))
          val dateStr = "20" + nested.get(0).getText
          val foreign = nested.get(1).getText.split("\n").head.replaceAll(",", "").toInt
          val organization = nested.get(2).getText.replaceAll(",", "").toInt
          val date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy.MM.dd"))
          println(date, foreign, organization)

          TrendItem(date, foreign, organization)
      }.toList

      val trend = Trend(code, items)

      println(trend)

      driver.quit()
    }
  }
}


