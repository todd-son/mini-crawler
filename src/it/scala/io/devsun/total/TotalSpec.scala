package io.devsun.total

import io.devsun.total.TotalModule.{diffRecommendReportService, mostRecommendReportService, mostRecommendTrackingService, newbieRecommendReportService, newbieTrackingService, reportCrawlService, trendCrawlService, trendRepository}
import org.scalatest.WordSpec

import java.time.LocalDate

class TotalSpec extends WordSpec {
  val today = LocalDate.now()

  "TodayReport" should {
    "crawl" in {
      val reports = reportCrawlService.crawl(today)

      reports.foreach {
        report =>
          trendCrawlService.crawl(report.code)
      }

      trendCrawlService.close()
    }

    "most recommended" in {
      val report = mostRecommendReportService.calculate(today.minusDays(30), today)
      println(report)
    }

    "newbie" in {
      val reports = newbieRecommendReportService.calculate(today.minusDays(30), today)

      reports.filter(newbie => newbie.targetPrice > 0).foreach { report => print(report)}
    }

    "most recommend tracking" in {
      val trackings = mostRecommendTrackingService.track(today.minusDays(14), today)

      trackings.foreach { tracking => print(tracking)}
    }

    "newbie recommend tracking" in {
      val trackings = newbieTrackingService.track(today.minusDays(14), today)

      trackings.foreach { tracking => print(tracking)}
    }

    "max diff ratio recommend" in {
      val diff = diffRecommendReportService.calculate(today.minusDays(30), today)

      println(diff)
    }
  }
}
