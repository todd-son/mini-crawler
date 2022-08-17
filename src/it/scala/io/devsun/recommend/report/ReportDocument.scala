package io.devsun.recommend.report

import org.mongodb.scala.Document
import org.mongodb.scala.bson.BsonDecimal128

import java.time.LocalDate

object ReportDocument {
  def of(report: Report): Document =
    Document(
      "date" -> report.date.toString,
      "code" -> report.code,
      "name" -> report.name,
      "targetPrice" -> report.targetPrice,
      "description" -> report.description
    )

  def as(document: Document): Report =
    Report(
      LocalDate.parse(document.getString("date")),
      document.getString("code"),
      document.getString("name"),
      document.getString("description"),
      document.getOrElse("targetPrice", BsonDecimal128(0)).asDecimal128().getValue.bigDecimalValue()
    )
}
