package io.devsun.recommend

import io.devsun.recommend.track.DailyPrice

trait StockApi {
  def findByCode(code: String, name: String): Stock

  def findDailyPricesByCode(code: String): List[DailyPrice]
}
