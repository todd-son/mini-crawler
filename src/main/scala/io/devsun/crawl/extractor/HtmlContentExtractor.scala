package io.devsun.crawl.extractor

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._

class HtmlContentExtractor(prefix: String, postfix: String) extends ContentExtractor {
  override def extract(responseBody: String, path: String): Seq[String] = {

    val browser = new JsoupBrowser()
    val doc = browser.parseString(prefix + responseBody + postfix)

    val items = doc >> elementList(path)

    items.map(element => prefix + element.innerHtml + postfix)
  }
}
