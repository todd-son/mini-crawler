package io.devsun.crawl.extractor

import io.devsun.crawl.extractor.HtmlContentExtractorSpec.html
import org.scalatest.WordSpec

import scala.io.Source

class HtmlContentExtractorSpec extends WordSpec {
  "HtmlContentExtractor" should {
    "extract" in {
//      println(html)
      val items = new HtmlContentExtractor("<table>", "</table>").extract(html, "table>tbody>tr")

      println(items.head)

//      items.foreach {
//        item =>
//          println(item)
//      }

    }
  }
}

object HtmlContentExtractorSpec {
  val html =
    Source
      .fromFile("/Users/todd/kakao/projects/mini-crawler/src/test/resources/io/devsun/crawl/extractor/sample.html")
      .getLines().mkString("\n")

}
