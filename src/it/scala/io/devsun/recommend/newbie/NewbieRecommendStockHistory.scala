package io.devsun.recommend.newbie

import io.devsun.recommend.most.RecommendStockHistory

import java.time.LocalDate

case class NewbieRecommendStockHistory(date: LocalDate, code: String, name: String) extends RecommendStockHistory {

}
