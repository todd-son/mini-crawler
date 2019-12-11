package io.devsun.crawl

import io.devsun.crawl.extractor.XmlContentExtractor
import org.json.XML

case class HttpCrawler() extends Crawler {
  import sttp.client.quick._

  override def crawl(url: String, fragmentPath: String): List[Item] = {
    val response = quickRequest.get(uri"""$url""").send()

    val contents = XmlContentExtractor.extract(response.body, fragmentPath)

    contents.map(xml => JsonItem(XML.toJSONObject(xml).toString(2)))
  }
}
