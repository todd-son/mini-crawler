package io.devsun.crawl.analysis.total

import io.devsun.recommend.trend.Trend
import org.mongodb.scala.Document

object TrendDocument {
  def of(trend: Trend): Document = {
    val items = trend.items.map { item =>
      Document(
        "date" -> item.date.toString,
        "foreign" -> item.foreign,
        "organization" -> item.organization
      )
    }

    Document(
      "code" -> trend.code,
      "items" -> items
    )
  }
}
