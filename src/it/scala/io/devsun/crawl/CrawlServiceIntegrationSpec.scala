package io.devsun.crawl

import io.devsun.crawl.extractor.XmlContentExtractor
import org.scalatest.WordSpec
import org.slf4j.LoggerFactory

class CrawlServiceIntegrationSpec extends WordSpec {
  private val crawlService = new CrawlService(HttpCrawler(XmlContentExtractor), "item")
  private val logger = LoggerFactory.getLogger(this.getClass)

  "CrawlService" should {
    "crawl" in {
      val url = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchStay?serviceKey=ndnUTHcxjSuzEwUB%2FR8ME20WRMp0NoYhmqmySaJjd%2BEojebYJ6VkFUWwpowkyKX1lvhQAV%2BBdtdp5%2FdEodFQdA%3D%3D&numOfRows=100&pageNo={0}&MobileOS=ETC&MobileApp=AppTest&arrange=A&listYN=Y"
      val items = crawlService.crawl(url)

      logger.debug("{}", items)
    }
  }
}
