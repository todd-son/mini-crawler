package io.devsun.crawler

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

object XmlContentExtractor extends ContentExtractor {
  import scala.xml._

  override def extract(responseBody: String, path: String): Seq[String] = {
    val xml = XML.loadString(responseBody)
    val nodeSeq = xml \\ path
    nodeSeq.theSeq.map(node => node.toString()).toSeq
  }
}
