package io.devsun.crawler

trait ContentExtractor {
  def extract(responseBody: String, path: String): Seq[String]
}
