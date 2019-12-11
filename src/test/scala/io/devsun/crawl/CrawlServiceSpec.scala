package io.devsun.crawl

import org.mockito.Mockito._
import org.scalatest.WordSpec

class CrawlServiceSpec extends WordSpec {
  private val crawler = mock(classOf[Crawler])
  private val crawlService = new CrawlService(crawler, "item")
  private val json = """{"name":"hello"}"""

  when(crawler.crawl("http://www.daum.net?pageNo=1&appKey=1234", "item"))
    .thenReturn(List(JsonItem(json), JsonItem(json), JsonItem(json)))
  when(crawler.crawl("http://www.daum.net?pageNo=2&appKey=1234", "item"))
    .thenReturn(List(JsonItem(json), JsonItem(json)))
  when(crawler.crawl("http://www.daum.net?pageNo=3&appKey=1234", "item"))
    .thenReturn(Nil)

"CrawlService" should {
    "crawl" in {
      val results = crawlService.crawl("http://www.daum.net?pageNo={0}&appKey=1234")
      assert(results.size == 5)
    }
  }
}



