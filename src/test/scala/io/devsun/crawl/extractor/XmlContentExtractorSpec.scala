package io.devsun.crawl.extractor

import org.scalatest.WordSpec

class XmlContentExtractorSpec extends WordSpec {
  "XmlContentExtractor" should {
    "extract content by xpath" in {
      val xml =
        """
          |<items>
          | <item>1</item>
          | <item>2</item>
          |</items>
          |""".stripMargin

      val path = "item"
      val items = XmlContentExtractor.extract(xml, path)
      assert(items.size == 2)
    }
  }
}


