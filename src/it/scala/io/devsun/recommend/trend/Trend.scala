package io.devsun.recommend.trend

import io.devsun.crawl.analysis.total.TrendItem

case class Trend(code: String, items: List[TrendItem]) {}
