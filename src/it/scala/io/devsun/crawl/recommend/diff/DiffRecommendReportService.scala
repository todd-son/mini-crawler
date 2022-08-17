package io.devsun.crawl.recommend.diff

import io.devsun.recommend.{Stock, StockApi}
import io.devsun.recommend.report.ReportRepository

import java.time.LocalDate

class DiffRecommendReportService(reportRepository: ReportRepository, stockApi: StockApi, diffRecommendReportRepository: DiffRecommendReportRepository) {

  def calculate(startDate: LocalDate, endDate: LocalDate): DiffRecommendReport = {
    val reports = reportRepository.findByStartDateAndEndDate(startDate, endDate)

    val recommended = reports
      .groupBy(report => report.code)
      .map {
        case (code, rs) =>
          val stock = stockApi.findByCode(code, rs.head.name)
          println(stock)
          DiffRecommendReportItem(stock, rs)
      }
      .toList
      .sortWith {
        case (r1, r2) =>
          r1.diffRatio > r2.diffRatio
      }
      .take(10)

    diffRecommendReportRepository.save(DiffRecommendReport(endDate, recommended))
  }

}
