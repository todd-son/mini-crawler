package io.devsun.crawler

import io.devsun.crawl.{Crawler, Item, JsonItem}
import org.json.{JSONObject, XML}

case class HttpCrawler() extends Crawler {
  import sttp.client.quick._

  override def crawl(url: String, fragmentPath: String): Seq[Item] = {
    val response = quickRequest.get(uri"""$url""").send()

    val contents = XmlContentExtractor.extract(response.body, fragmentPath)

    contents.map(xml => JsonItem(XML.toJSONObject(xml).toString(4)))
  }
}
