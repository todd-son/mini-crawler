package io.devsun.recommend.most

import io.devsun.recommend.Stock
import io.devsun.recommend.report.Report

trait RecommendReport {
  val stock: Stock

  val reports: List[Report]

  def targetPrice: BigDecimal = {
    val result = reports.foldLeft((0, BigDecimal(0))) { case (acc, report) =>
      if (report.targetPrice != BigDecimal(0)) {
        (acc._1 + 1, acc._2 + report.targetPrice)
      } else {
        acc
      }
    }

    if ( result._2 > 0)
      (result._2 / result._1).intValue
    else
      0
  }

  def diffRatio: BigDecimal = {
    if (targetPrice != 0 && stock.currentPrice != 0)
      (targetPrice - stock.currentPrice) / stock.currentPrice * 100
    else
      0
  }
}
