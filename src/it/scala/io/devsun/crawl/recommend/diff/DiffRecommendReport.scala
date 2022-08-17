package io.devsun.crawl.recommend.diff

import java.time.LocalDate

case class DiffRecommendReport(date: LocalDate, items: List[DiffRecommendReportItem]) {
  override def toString: String = {
    s"""
       |===================================
       |30일간 최다 목표가 괴리율 리포트
       |===================================
       |${items.mkString("-----------------------------------")}
       |""".stripMargin
  }
}
