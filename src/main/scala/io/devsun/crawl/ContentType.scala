package io.devsun.crawl

sealed trait ContentType

object ContentType {
  case object XML extends ContentType

  case object JSON extends ContentType
}


