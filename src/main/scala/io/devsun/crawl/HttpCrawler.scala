package io.devsun.crawl

import io.devsun.crawl.extractor.{ContentExtractor, HtmlContentExtractor}

case class HttpCrawler(contentExtractor: ContentExtractor) extends Crawler {
  import sttp.client.quick._

  override def crawl(url: String, fragmentPath: String): List[Item] = {
    val response = quickRequest.get(uri"""$url""").send()

    val payload = contentExtractor.extract(response.body, fragmentPath)

    contentExtractor match {
      case _: HtmlContentExtractor =>
        payload.map(item => HtmlItem(item)).toList
    }
  }
}
