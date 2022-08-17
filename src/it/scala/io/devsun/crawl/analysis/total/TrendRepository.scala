package io.devsun.crawl.analysis.total

import io.devsun.recommend.trend.Trend

trait TrendRepository {
  def save(trend: Trend): Trend
}
