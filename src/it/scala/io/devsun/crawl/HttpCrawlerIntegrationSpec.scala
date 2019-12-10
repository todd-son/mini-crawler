package io.devsun.crawl

import io.devsun.crawler.HttpCrawler
import org.scalatest.WordSpec
import org.slf4j.LoggerFactory

class HttpCrawlerIntegrationSpec extends WordSpec {
  private val crawler = HttpCrawler()
  private val logger = LoggerFactory.getLogger(this.getClass)

  "HttpCrawler" should {
    "crawl xml data via http" in {
      val url = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchStay?serviceKey=ndnUTHcxjSuzEwUB%2FR8ME20WRMp0NoYhmqmySaJjd%2BEojebYJ6VkFUWwpowkyKX1lvhQAV%2BBdtdp5%2FdEodFQdA%3D%3D&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&arrange=A&listYN=Y"
      val items = crawler.crawl(url, "item")
      logger.info("{}", items)
    }
  }
}
