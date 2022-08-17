package io.devsun.recommend

import io.devsun.recommend.most.{MongoMostRecommendHistoryRepository, MostRecommendReportService}
import io.devsun.recommend.report.MongoReportRepository
import org.mongodb.scala.MongoClient
import org.scalatest.{WordSpec, stats}

import java.time.LocalDate

class MostRecommendServiceIntegrationSpec extends WordSpec {
  val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0b2tlbiIsImF1ZCI6ImE0M2UyNmRhLTg1NDItNDIzOS1hYTIyLTYwOTFiMDI0NGE5NCIsImlzcyI6InVub2d3IiwiZXhwIjoxNjU1NTQ4NDg3LCJpYXQiOjE2NTU0NjIwODcsImp0aSI6IlBTRGFZMEREd200TmxJN1BjNnkwNnRiWXZ3NWFGVlNIeEFnSiJ9.O-AXFay6kTwreRn98CVh31rqbog1UmU8KXSUjuJ31TV4RSxMKUv56NYcVNCwP-KtXnT0LrKodYi_i4ha2O4D_w"
  private val client: MongoClient = MongoClient(

    "mongodb://localhost:27017"
  )
  val service = new MostRecommendReportService(
    new MongoReportRepository(
      client
    ),
    new MongoMostRecommendHistoryRepository(
      client
    ),
    KisStockApi()
  )

  "MostRecommend" should {
    "calculate specific day" in {
      val date = LocalDate.of(2022, 6, 15)

      val mostRecommendReport = service.calculate(
        date.minusDays(30),
        date
      )

      println(mostRecommendReport)
    }

    "calculate today" in {
      val mostRecommendReport = service.calculate(
        LocalDate.now().minusDays(30),
        LocalDate.now()
      )

      println(mostRecommendReport)
    }
  }
}






