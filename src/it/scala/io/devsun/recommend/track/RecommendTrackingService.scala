package io.devsun.recommend.track

import io.devsun.recommend.StockApi
import io.devsun.recommend.most.{RecommendStockHistory, RecommendStockHistoryRepository}

import java.time.LocalDate

class RecommendTrackingService[T <: RecommendStockHistory](historyRepository: RecommendStockHistoryRepository[T], stockApi: StockApi) {
  def track(startDate: LocalDate, endDate: LocalDate): List[RecommendTracking] = {
    val histories = historyRepository.findByDate(startDate, endDate)

    histories.map { history =>
      val prices = stockApi.findDailyPricesByCode(history.code)
      val stock = stockApi.findByCode(history.code, history.name)
      RecommendTracking(history.date, stock, prices)
    }
  }
}
