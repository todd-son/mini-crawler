package io.devsun.recommend.newbie

import io.devsun.recommend.most.RecommendStockHistoryRepository
import org.mongodb.scala.MongoClient
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.UpdateOptions
import org.mongodb.scala.model.Updates._

import java.time.LocalDate
import scala.concurrent.Await
import scala.concurrent.duration.Duration

class MongoNewbieRecommendHistoryRepository(mongoClient: MongoClient) extends RecommendStockHistoryRepository[NewbieRecommendStockHistory] {
  val db = mongoClient.getDatabase("stock")
  val historyCollection = db.getCollection("newbieRecommendHistory")

  override def save(history: NewbieRecommendStockHistory): NewbieRecommendStockHistory = {
    val dateStr = history.date.toString
    val code = history.code

    val doc = combine(
      set("date", dateStr),
      set("code", code),
      set("name", history.name)
    )

    val insert = historyCollection.updateOne(
      and(
        equal("date", dateStr),
        equal("code", history.code)
      ),
      doc,
      new UpdateOptions().upsert(true)
    )

    Await.result(insert.toFuture(), Duration.Inf)

    history
  }

  override def findByDate(startDate: LocalDate, endDate: LocalDate): List[NewbieRecommendStockHistory] = {
    val find = historyCollection.find(
      and(
        gte("date", startDate.toString),
        lte("date", endDate.toString)
      )
    ).map { doc =>
      NewbieRecommendStockHistory(
        LocalDate.parse(doc.getString("date")),
        doc.getString("code"),
        doc.getString("name")
      )
    }

    Await.result(find.toFuture(), Duration.Inf).toList
  }
}
