package io.devsun.recommend.most

import io.devsun.recommend.Stock
import io.devsun.recommend.report.Report

import java.time.LocalDate
import scala.math.BigDecimal.RoundingMode

case class MostRecommendReport(
    date: LocalDate,
    stock: Stock,
    reports: List[Report]
) extends RecommendReport {
  override def toString: String = {
    s"""
      |===================================
      |${date.toString} 30일간 최다 추천주
      |종목명 : ${stock.name}(${stock.code})
      |현재가: ${stock.currentPrice}
      |평균목표가: $targetPrice
      |목표가괴리율: ${diffRatio.setScale(2, RoundingMode.HALF_UP)}
      |PER : ${stock.per}
      |PBR : ${stock.pbr}
      |===================================
      |${reports
      .sortWith { case (s1, s2) => s1.date.isAfter(s2.date) }
      .mkString("===================================")}
      |""".stripMargin
  }
}