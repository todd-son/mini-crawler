package io.devsun.crawl.recommend.diff

import io.devsun.recommend.Stock
import io.devsun.recommend.most.RecommendReport
import io.devsun.recommend.report.Report

import scala.math.BigDecimal.RoundingMode

case class DiffRecommendReportItem(stock: Stock, reports: List[Report]) extends RecommendReport {
  val date = reports.maxBy(r => r.date).date

  override def toString: String = {
    s"""
      |===================================
      |종목명 : ${stock.name}(${stock.code})
      |현재가: ${stock.currentPrice}
      |평균목표가: $targetPrice
      |목표가괴리율: ${diffRatio.setScale(2, RoundingMode.HALF_UP)}
      |PER : ${stock.per}
      |PBR : ${stock.pbr}
      |최근 추천일 : ${date}
      |===================================
      |""".stripMargin
  }
}

