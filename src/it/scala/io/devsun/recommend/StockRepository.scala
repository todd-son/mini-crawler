package io.devsun.recommend

trait StockRepository {

  def save(stock: Stock): Stock

}
