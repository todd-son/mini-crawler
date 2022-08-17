package io.devsun.crawl

import io.devsun.crawl.analysis.total.{TrendItem, TrendRepository}
import io.devsun.recommend.trend.Trend
import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TrendCrawlService(trendRepository: TrendRepository) {

  System.setProperty("webdriver.chrome.driver", "/kakao/programs/chromedriver")
  val driver = new ChromeDriver

  def crawl(code: String): Unit = {
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

    trendRepository.save(trend)
  }

  def close(): Any = driver.quit()
}
