package io.devsun.crawl

class CrawlService(crawler: Crawler, fragmentPath: String) {
  def crawl(url: String): List[Item] = {
    def next(page: Int): List[Item] = {
      val nextUrl = NextPageUrl(url, page)

      crawler.crawl(nextUrl.url, fragmentPath) match {
        case Nil => Nil
        case list if list != null => list ++ next(page + 1)
      }
    }

    val pageNo = 1
    next(pageNo)
  }
}
