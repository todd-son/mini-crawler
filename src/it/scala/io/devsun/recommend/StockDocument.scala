package io.devsun.recommend

import org.mongodb.scala.Document
import org.mongodb.scala.bson.BsonDecimal128

object StockDocument {
  def of(stock: Stock): Document =
    Document(
      "code" -> stock.code,
      "name" -> stock.name,
      "per" -> stock.per,
      "pbr" -> stock.pbr,
      "currentPrice" -> stock.currentPrice
    )

  def as(doc: Document): Stock =
    Stock(
      doc.getString("code"),
      doc.getString("name"),
      doc.getOrElse("per", BsonDecimal128(0)).asDecimal128().getValue.bigDecimalValue(),
      doc.getOrElse("pbr", BsonDecimal128(0)).asDecimal128().getValue.bigDecimalValue(),
      doc.getOrElse("currentPrice", BsonDecimal128(0)).asDecimal128().getValue.bigDecimalValue(),
    )
}
