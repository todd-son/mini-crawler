package io.devsun.recommend.report

import java.time.LocalDate

case class Report(
    date: LocalDate,
    code: String,
    name: String,
    description: String,
    targetPrice: BigDecimal
) {
  override def toString: String =
    s"""
      |추천일 : $date
      |목표가 : ${if (targetPrice != 0 ) targetPrice else "Not Rated"}
      |설명 : $description
      |""".stripMargin
}
