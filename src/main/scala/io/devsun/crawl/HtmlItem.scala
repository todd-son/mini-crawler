package io.devsun.crawl

import org.jsoup.Jsoup

case class HtmlItem(payload: String) extends Item {
  val doc = Jsoup.parse(payload)

  override def getString(path: String): Option[String] =
    Some(
      doc.selectXpath(path).text()
    )
}
