package io.devsun.crawl

trait Crawler {
  def crawl(url: String, fragmentPath: String): List[Item]
}

