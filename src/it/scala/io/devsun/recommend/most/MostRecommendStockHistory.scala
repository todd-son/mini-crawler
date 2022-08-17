package io.devsun.recommend.most

import java.time.LocalDate

case class MostRecommendStockHistory(date: LocalDate, code: String, name: String) extends RecommendStockHistory {

}
