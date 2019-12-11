package io.devsun.crawl.extractor

object XmlContentExtractor extends ContentExtractor {
  import scala.xml._

  override def extract(responseBody: String, path: String): List[String] = {
    val xml = XML.loadString(responseBody)
    val nodeSeq = xml \\ path
    nodeSeq.theSeq.map(node => node.toString()).toList
  }
}
