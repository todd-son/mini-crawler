package io.devsun.recommend.track

import java.time.LocalDate

case class DailyPrice(
    date: LocalDate,
    startPrice: BigDecimal,
    highPrice: BigDecimal,
    endPrice: BigDecimal
) {
  override def toString: String = {
    s"""
       |일자 : $date
       |시초가 : $startPrice
       |최고가 : $highPrice
       |종가 : $endPrice
       |""".stripMargin
  }
}
