package io.devsun.crawl.recommend.diff
import io.devsun.recommend.StockDocument
import io.devsun.recommend.report.ReportDocument
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.{Document, MongoClient}

import java.time.LocalDate
import scala.concurrent.Await
import scala.concurrent.duration.Duration

class MongoDiffRecommendReportRepository(mongoClient: MongoClient) extends DiffRecommendReportRepository {
  val db = mongoClient.getDatabase("stock")
  val collection = db.getCollection("mongoDiffRecommendReport")

  override def save(report: DiffRecommendReport): DiffRecommendReport = {
    val dateStr = report.date.toString

    val doc = Document(
      "date" -> dateStr,
      "items" -> report.items.map(item => itemToDoc(item))
    )

    Await.result(collection.deleteOne(equal("date", dateStr)).toFuture(), Duration.Inf)
    Await.result(collection.insertOne(doc).toFuture(), Duration.Inf)

    report
  }

  def itemToDoc(item: DiffRecommendReportItem): Document = {
    Document(
      "stock" -> StockDocument.of(item.stock),
      "reports" -> item.reports.map(report => ReportDocument.of(report))
    )
  }

  override def findByDate(date: LocalDate): DiffRecommendReport = ???
}
