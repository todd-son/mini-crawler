package io.devsun.crawl

trait Item {
  def getString(path: String): Option[String]
}
