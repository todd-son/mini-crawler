package io.devsun.recommend.most

import io.devsun.recommend._
import io.devsun.recommend.report.ReportRepository

import java.time.LocalDate

class MostRecommendReportService(
                                  reportRepository: ReportRepository,
                                  historyRepository: MongoMostRecommendHistoryRepository,
                                  stockApi: StockApi
) {
  def calculate(
      startDate: LocalDate,
      endDate: LocalDate
  ): MostRecommendReport = {
    val reports = reportRepository.findByStartDateAndEndDate(startDate, endDate)
    val grouped = reports.groupBy(report => report.code)

    val sorted = grouped.toList
      .sortBy { case (_, reports) =>
        reports.size
      }

    val filtered = sorted
      .filter { case (_, reports) =>
        reports.exists(report => report.date.isEqual(endDate))
      }

    val mostRecommended = filtered.last._2

    val name = mostRecommended.head.name

    val stock = stockApi.findByCode(mostRecommended.head.code, name)

    historyRepository.save(
      MostRecommendStockHistory(endDate, stock.code, name)
    )

    most.MostRecommendReport(endDate, stock, mostRecommended)
  }
}
