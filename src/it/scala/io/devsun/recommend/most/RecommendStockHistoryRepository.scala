package io.devsun.recommend.most

import java.time.LocalDate

trait RecommendStockHistoryRepository[T <: RecommendStockHistory] {
  def save(history: T): T

  def findByDate(
      startDate: LocalDate,
      endDate: LocalDate
  ): List[T]
}
