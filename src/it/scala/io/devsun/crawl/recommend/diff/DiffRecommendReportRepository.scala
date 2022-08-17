package io.devsun.crawl.recommend.diff

import java.time.LocalDate

trait DiffRecommendReportRepository {
  def save(report: DiffRecommendReport): DiffRecommendReport

  def findByDate(date: LocalDate): DiffRecommendReport
}
