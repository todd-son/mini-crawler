package io.devsun.recommend.most

import java.time.LocalDate

trait RecommendStockHistory {
  val date: LocalDate

  val code: String

  val name: String
}
