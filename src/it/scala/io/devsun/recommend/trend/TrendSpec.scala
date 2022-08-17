package io.devsun.recommend.trend

import io.devsun.total.TotalModule._
import org.scalatest.WordSpec

class TrendSpec extends WordSpec {
  "Trend" should {
    "print" in {
      trendRepository.findByCode("A018880")
      println()
    }
  }

}
