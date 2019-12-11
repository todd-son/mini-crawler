package io.devsun.crawl

sealed trait Url {
  def next(): String
}

case class NextPageUrl(baseUrl: String, pageNo: Int) {
  def url: String = baseUrl.replace("{0}", pageNo.toString)
}


