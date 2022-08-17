package io.devsun.recommend

import org.scalatest.WordSpec

class KisStockApiSpec extends WordSpec {
  val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0b2tlbiIsImF1ZCI6ImE0M2UyNmRhLTg1NDItNDIzOS1hYTIyLTYwOTFiMDI0NGE5NCIsImlzcyI6InVub2d3IiwiZXhwIjoxNjU1NTQ4NDg3LCJpYXQiOjE2NTU0NjIwODcsImp0aSI6IlBTRGFZMEREd200TmxJN1BjNnkwNnRiWXZ3NWFGVlNIeEFnSiJ9.O-AXFay6kTwreRn98CVh31rqbog1UmU8KXSUjuJ31TV4RSxMKUv56NYcVNCwP-KtXnT0LrKodYi_i4ha2O4D_w"

  "KisStockApi" should {
    "get access token when initialized" in {
      val api = KisStockApi()
      api.findByCode("A011070", "카카오")
    }

    "get current stock" in {
      val api = new KisStockApi(token)
      val stock = api.findByCode("A011070", "카카오")
      println(stock)
    }

    "get daily price" in {
      val api = new KisStockApi(token)
      val prices = api.findDailyPricesByCode("A035720")
      println(prices)
    }
  }
}
