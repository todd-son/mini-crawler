package io.devsun.recommend

import io.circe.Json
import sttp.client.quick._
import io.circe.parser._
import io.devsun.recommend.track.DailyPrice

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class KisStockApi(accessToken: String) extends StockApi {
  val appkey = "PSDaY0DDwm4NlI7Pc6y06tbYvw5aFVSHxAgJ"
  val appsecret = "WYCYZ2Sv0iQUymamuup8ISgDRmncPTs9aoHR5XfnMmaw14pxv37ChSnOkhSgG7X5k8Qepj0SewHE7un/YMrai7f/LidmVB8vYjwUvXrcZyNwXPGBDHSiDd1EM/6M/bZftoAHkb1+LGbrF++28Z29xCfIB0I3TmZIyRRghVAaRYt4eyvOP74="

  override def findByCode(code: String, name: String): Stock = {
    retry(10)(() => getStock(code, name))
  }

  private def getStock(code: String, name: String) = {
    val jcode = code.substring(1)
    val uri = uri"""https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/quotations/inquire-price?fid_cond_mrkt_div_code=J&fid_input_iscd=${jcode}"""

    val res = quickRequest.get(uri).headers(
      Map(
        "authorization" -> s"Bearer $accessToken",
        "appkey" -> appkey,
        "appsecret" -> appsecret,
        "custtype" -> "P",
        "Content-Type" -> "application/json",
        "tr_id" -> "FHKST01010100"

      )
    ).send()

    val json = parse(res.body).getOrElse(Json.Null)
    val cursor = json.hcursor
    val output = cursor.downField("output")
    val per = output.downField("per").as[String].getOrElse("0")
    val pbr = output.downField("pbr").as[String].getOrElse("0")
    val currentPrice = output.downField("stck_prpr").as[String].getOrElse("0")

    Stock(code, name, BigDecimal(per), BigDecimal(pbr), BigDecimal(currentPrice))
  }

  def retry(n: Int)(fn: () => Stock): Stock = {
    val res = fn()

    if (res.currentPrice > 0) {
      res
    } else {
      Thread.sleep(2000)
      if (n > 1)
        retry(n - 1)(fn)
      else
        throw new IllegalStateException()
    }
  }

  override def findDailyPricesByCode(code: String): List[DailyPrice] = {
    val jcode = code.substring(1)
    val uri = uri"""https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/quotations/inquire-daily-price?fid_cond_mrkt_div_code=J&fid_input_iscd=$jcode&fid_org_adj_prc=0000000001&fid_period_div_code=D"""

    val res = quickRequest.get(uri).headers(
      Map(
        "authorization" -> s"Bearer $accessToken",
        "appkey" -> appkey,
        "appsecret" -> appsecret,
        "custtype" -> "P",
        "Content-Type" -> "application/json",
        "tr_id" -> "FHKST01010400"

      )
    ).send()

    val json = parse(res.body).getOrElse(Json.Null)
    val cursor = json.hcursor

    cursor.downField("output").as[Json] match {
      case Right(items) =>
        items.asArray match {
          case Some(array) =>
            array.map { item =>
              val cursor = item.hcursor
              val date = cursor.downField("stck_bsop_date").as[String].getOrElse("")
              val startPrice = cursor.downField("stck_oprc").as[String].getOrElse("")
              val highPrice = cursor.downField("stck_hgpr").as[String].getOrElse("")
              val endPrice = cursor.downField("stck_clpr").as[String].getOrElse("")

              track.DailyPrice(
                LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd")),
                BigDecimal(startPrice),
                BigDecimal(highPrice),
                BigDecimal(endPrice)
              )
            }.toList

          case None =>
            Nil
        }
      case _ =>
        Nil
    }
  }
}

object KisStockApi {
  def apply(): KisStockApi = {
    val uri = uri"""https://openapi.koreainvestment.com:9443/oauth2/tokenP"""
    val authBody =
      """
        |{
        |  "grant_type": "client_credentials",
        |  "appkey": "PSDaY0DDwm4NlI7Pc6y06tbYvw5aFVSHxAgJ",
        |  "appsecret": "WYCYZ2Sv0iQUymamuup8ISgDRmncPTs9aoHR5XfnMmaw14pxv37ChSnOkhSgG7X5k8Qepj0SewHE7un/YMrai7f/LidmVB8vYjwUvXrcZyNwXPGBDHSiDd1EM/6M/bZftoAHkb1+LGbrF++28Z29xCfIB0I3TmZIyRRghVAaRYt4eyvOP74="
        |}
        |""".stripMargin

    val res = quickRequest.post(uri).header("Content-Type", "application/json").body(authBody).send()

    val json = parse(res.body).getOrElse(Json.Null)
    val accessToken = json.hcursor.downField("access_token").as[String].getOrElse("")

    new KisStockApi(accessToken)
  }
}
