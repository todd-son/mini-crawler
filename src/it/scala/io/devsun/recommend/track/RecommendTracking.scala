package io.devsun.recommend.track

import io.devsun.recommend.Stock

import java.time.LocalDate
import java.time.temporal.ChronoUnit
import scala.math.BigDecimal.RoundingMode

case class RecommendTracking(
    date: LocalDate,
    stock: Stock,
    prices: List[DailyPrice]
) {
  val afterPrices: List[DailyPrice] = prices
    .filter(price =>
      price.date.isAfter(date) && price.date.isBefore(LocalDate.now())
    )

  val basePrice: DailyPrice = prices.find(price => price.date.isEqual(date)).getOrElse(
    DailyPrice(
      date, 0, 0, 0
    )
  )

  val todayPrice: Option[DailyPrice] = afterPrices.headOption

  val highPrice: DailyPrice =
    (afterPrices ++ List(basePrice)).maxBy(price => price.highPrice)

  def highRatio: BigDecimal =
    (highPrice.highPrice - basePrice.startPrice) / highPrice.startPrice * 100

  override def toString: String = {
    todayPrice match {
      case Some(today) =>
        s"""
           |=======================================
           |추천일 : ${basePrice.date}
           |종목명 : ${stock.name}
           |추천일 시초가: ${basePrice.startPrice}
           |---------------------------------------
           |최고가 달성일 : ${highPrice.date}
           |최고가 : ${highPrice.highPrice}
           |최고가 수익률 : ${highRatio.setScale(2, RoundingMode.HALF_UP)}
           |최고가 걸린 일자 : ${ChronoUnit.DAYS.between(
          basePrice.date,
          highPrice.date
        )}
           |---------------------------------------
           |최근 : ${today.date}
           |최근 시초가 : ${today.startPrice}
           |최근 최고가 : ${today.highPrice}
           |최근 종가 : ${today.endPrice}
           |=======================================
           |""".stripMargin
      case None =>
        ""
    }
  }
}
