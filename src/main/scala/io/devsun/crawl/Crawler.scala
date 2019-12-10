package io.devsun.crawl

trait Crawler {
  def crawl(url: String, fragmentPath: String): Seq[Item]
}

