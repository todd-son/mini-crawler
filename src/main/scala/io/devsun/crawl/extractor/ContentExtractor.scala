package io.devsun.crawl.extractor

trait ContentExtractor {
  def extract(responseBody: String, path: String): Seq[String]
}
