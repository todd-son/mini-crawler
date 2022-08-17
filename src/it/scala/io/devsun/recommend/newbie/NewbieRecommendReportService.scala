package io.devsun.recommend.newbie

import io.devsun.recommend.StockApi
import io.devsun.recommend.report.{Report, ReportRepository}

import java.time.LocalDate

class NewbieRecommendReportService(
    reportRepository: ReportRepository,
    stockApi: StockApi,
    newbieRecommendStockHistoryRepository: MongoNewbieRecommendHistoryRepository
) {
  def calculate(
      startDate: LocalDate,
      endDate: LocalDate
  ): List[NewbieRecommendReport] = {
    val reports = reportRepository.findByStartDateAndEndDate(startDate, endDate)
    val grouped = reports.groupBy(report => report.code)

    val filtered = grouped.filter { case (_: String, rs: List[Report]) =>
      rs.forall(r => r.date.isEqual(endDate))
    }

    val recommended = filtered
      .map { case (_: String, rs: List[Report]) =>
        val stock = stockApi.findByCode(rs.head.code, rs.head.name)
        NewbieRecommendReport(endDate, stock, rs)
      }
      .filter(r => r.targetPrice > 0)
      .toList

    recommended.foreach { r =>
      newbieRecommendStockHistoryRepository.save(
        NewbieRecommendStockHistory(
          endDate,
          r.stock.code,
          r.stock.name
        )
      )
    }

    recommended
  }
}
