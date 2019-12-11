package io.devsun.crawl

import org.scalatest.WordSpec

class NextPageUrlSpec extends WordSpec {
  "NextPageUrl" should {
    "interpolate by pageNo" in {
      val baseUrl = "http://www.daum.net?pageNo={0}&appKey=1234"

      assert(NextPageUrl(baseUrl, 1).url == "http://www.daum.net?pageNo=1&appKey=1234")
      assert(NextPageUrl(baseUrl, 2).url == "http://www.daum.net?pageNo=2&appKey=1234")
    }
  }

}
