package io.devsun.recommend.report

import java.time.LocalDate

trait ReportRepository {
  def findByStartDateAndEndDate(
      startDate: LocalDate,
      endDate: LocalDate
  ): List[Report]

  def save(report: Report): Report
}
