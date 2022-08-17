package io.devsun.crawl.analysis.total

import io.devsun.recommend.trend.Trend
import org.mongodb.scala.MongoClient
import org.mongodb.scala.model.Filters
import org.mongodb.scala.result.DeleteResult

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class MongoTrendRepository(mongoClient: MongoClient) extends TrendRepository {
  val database = mongoClient.getDatabase("stock")

  val collection = database.getCollection("trend")
  override def save(trend: Trend): Trend = {
    val deleteOne = collection.deleteOne(Filters.equal("code", trend.code)).toFuture()

    println(s"code : ${trend.code}")

    val result: DeleteResult = Await.result(deleteOne, Duration.Inf)

    Thread.sleep(500)

    val insertOne = collection.insertOne(TrendDocument.of(trend)).toFuture()
    Await.result(insertOne, Duration.Inf)

    trend
  }

  def findByCode(code: String): Trend = {
    ???
  }
}
