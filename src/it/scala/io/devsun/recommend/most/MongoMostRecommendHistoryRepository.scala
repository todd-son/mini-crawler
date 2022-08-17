package io.devsun.recommend.most

import io.devsun.recommend.most
import org.mongodb.scala.MongoClient
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.UpdateOptions
import org.mongodb.scala.model.Updates._

import java.time.LocalDate
import scala.concurrent.Await
import scala.concurrent.duration.Duration

class MongoMostRecommendHistoryRepository(mongoClient: MongoClient) extends RecommendStockHistoryRepository[MostRecommendStockHistory] {
  val db = mongoClient.getDatabase("stock")
  val historyCollection = db.getCollection("mostRecommendHistory")

  override def save(history: MostRecommendStockHistory): MostRecommendStockHistory = {
    val dateStr = history.date.toString
    val code = history.code

    val doc = combine(
      set("date", dateStr),
      set("code", code),
      set("name", history.name)
    )

    val updated = historyCollection.updateOne(
      and(
        equal("date", dateStr),
        equal("code", history.code)
      ),
      doc,
      new UpdateOptions().upsert(true)
    )

    Await.result(updated.toFuture(), Duration.Inf)

    history
  }

  override def findByDate(startDate: LocalDate, endDate: LocalDate): List[MostRecommendStockHistory] = {
    val find = historyCollection.find(
      and(
        gte("date", startDate.toString),
        lte("date", endDate.toString)
      )
    ).map { doc =>
      most.MostRecommendStockHistory(
        LocalDate.parse(doc.getString("date")),
        doc.getString("code"),
        doc.getString("name")
      )
    }

    Await.result(find.toFuture(), Duration.Inf).toList
  }
}
