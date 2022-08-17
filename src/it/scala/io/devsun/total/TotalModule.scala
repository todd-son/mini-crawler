package io.devsun.total

import io.devsun.crawl.analysis.total.MongoTrendRepository
import io.devsun.crawl.{ReportCrawlerService, TrendCrawlService}
import io.devsun.crawl.recommend.diff.{DiffRecommendReportService, MongoDiffRecommendReportRepository}
import io.devsun.recommend.KisStockApi
import io.devsun.recommend.most.{MongoMostRecommendHistoryRepository, MostRecommendReportService}
import io.devsun.recommend.newbie.{MongoNewbieRecommendHistoryRepository, NewbieRecommendReportService}
import io.devsun.recommend.report.MongoReportRepository
import io.devsun.recommend.track.RecommendTrackingService
import org.mongodb.scala.MongoClient

object TotalModule {
  val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0b2tlbiIsImF1ZCI6IjhhNzQ5NjVlLWI4YmEtNDY1Yi1hYjFlLTQ0MDAxODAyMWNhNCIsImlzcyI6InVub2d3IiwiZXhwIjoxNjYwMTgxMTE5LCJpYXQiOjE2NjAwOTQ3MTksImp0aSI6IlBTRGFZMEREd200TmxJN1BjNnkwNnRiWXZ3NWFGVlNIeEFnSiJ9.6wEIMHd5dH3NGYRnACtYfbLOMDD7g8CQqrf0Hi_vYLVkp17rMiz1pOAEpcM7NoC73daMxbVL1yxRg-6epWbCWQ"
  val mongoClient = MongoClient()

  val stockApi = new KisStockApi(token)

  val reportRepository = new MongoReportRepository(
    mongoClient
  )

  val trendRepository = new MongoTrendRepository(
    mongoClient
  )

  val reportCrawlService = new ReportCrawlerService(
    reportRepository
  )

  val trendCrawlService = new TrendCrawlService(
    trendRepository
  )

  val mostRecommendReportService = new MostRecommendReportService(
    reportRepository,
    new MongoMostRecommendHistoryRepository(mongoClient),
    stockApi
  )

  val newbieRecommendReportService = new NewbieRecommendReportService(
    reportRepository,
    stockApi,
    new MongoNewbieRecommendHistoryRepository(mongoClient)
  )

  val mostRecommendTrackingService = new RecommendTrackingService(
    new MongoMostRecommendHistoryRepository(
      mongoClient
    ),
    stockApi
  )

  val newbieTrackingService = new RecommendTrackingService(
    new MongoNewbieRecommendHistoryRepository(
      mongoClient
    ),
    stockApi
  )

  val diffRecommendReportService = new DiffRecommendReportService(
    reportRepository,
    stockApi,
    new MongoDiffRecommendReportRepository(
      mongoClient
    )
  )
}
