package io.devsun.recommend.track

import io.devsun.recommend.KisStockApi
import io.devsun.recommend.most.MongoMostRecommendHistoryRepository
import org.mongodb.scala.MongoClient
import org.scalatest.WordSpec

import java.time.LocalDate

class RecommendTrackingServiceIntegrationSpec extends WordSpec {
  val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0b2tlbiIsImF1ZCI6ImIzOWNkZmFiLTA0YWQtNDljZC1hMDJmLWVlZWI0MjU1YjE5MiIsImlzcyI6InVub2d3IiwiZXhwIjoxNjU1ODA2OTAyLCJpYXQiOjE2NTU3MjA1MDIsImp0aSI6IlBTRGFZMEREd200TmxJN1BjNnkwNnRiWXZ3NWFGVlNIeEFnSiJ9.O9Ge-nhWHYXvfOz0qbDfwrdRlGpRCgkg4TcjSh2G1Eif88ka4nFH8i3gxcih0KI9wHDeuBOyhkm54pTDu-7tMA"

  val service = new RecommendTrackingService(
    new MongoMostRecommendHistoryRepository(
      MongoClient()
    ),
    new KisStockApi(token)
  )

  "RecommendTrackingService" should {
    "track" in {
      val recommendDate = LocalDate.now()

      val trackings = service.track(recommendDate.minusDays(14), recommendDate)

      trackings.foreach { tracking => println(tracking)}
    }
  }
}
