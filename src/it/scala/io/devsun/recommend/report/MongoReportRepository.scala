package io.devsun.recommend.report

import org.mongodb.scala.MongoClient
import org.mongodb.scala.model.Filters
import org.mongodb.scala.model.Filters._

import java.time.LocalDate
import scala.concurrent.Await
import scala.concurrent.duration.Duration

class MongoReportRepository(val mongoClient: MongoClient)
    extends ReportRepository {
  val db = mongoClient.getDatabase("stock")
  val reportCollection = db.getCollection("report")

  override def findByStartDateAndEndDate(
      startDate: LocalDate,
      endDate: LocalDate
  ): List[Report] = {
    val stocks = reportCollection
      .find(
        and(
          gte("date", startDate.toString),
          lte("date", endDate.toString)
        )
      )
      .map { doc =>
        ReportDocument.as(doc)
      }
      .toFuture()

    Await.result(stocks, Duration.Inf).toList
  }

  override def save(report: Report): Report = {
    val doc = ReportDocument.of(report)

    val deleteOne = reportCollection
      .deleteOne(
        and(
          equal("code", report.code),
          equal("date", report.date.toString)
        )
      )
      .toFuture()
    val insertOne = reportCollection.insertOne(doc).toFuture()

    Await.result(deleteOne, Duration.Inf)
    Await.result(insertOne, Duration.Inf)

    report
  }
}
