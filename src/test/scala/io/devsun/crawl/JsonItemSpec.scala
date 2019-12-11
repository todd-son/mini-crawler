package io.devsun.crawl

import io.devsun.crawl.JsonItemSpec._
import org.scalatest.WordSpec
class JsonItemSpec extends WordSpec {
  "JsonItem" should {
    "return value by json path" in {
      val item = JsonItem(json)
      val result = item.getString("item.addr1")

      assert(result == Option("인천광역시 미추홀구 미추홀대로722번길 40"))
    }
  }
}

object JsonItemSpec {
  val json =
    """
      |{"item": {
      |    "readcount": 4185,
      |    "addr1": "인천광역시 미추홀구 미추홀대로722번길 40",
      |    "contentid": 1832039,
      |    "firstimage2": "http://tong.visitkorea.or.kr/cms/resource/51/1832051_image2_1.JPG",
      |    "title": "샾호텔",
      |    "areacode": 2,
      |    "createdtime": 20130730161506,
      |    "mapy": 37.4616750515,
      |    "contenttypeid": 32,
      |    "mapx": 126.6825676095,
      |    "cat2": "B0201",
      |    "cat3": "B02010900",
      |    "modifiedtime": 20190312162443,
      |    "cat1": "B02",
      |    "mlevel": 6,
      |    "sigungucode": 3,
      |    "tel": "032-426-0333",
      |    "firstimage": "http://tong.visitkorea.or.kr/cms/resource/51/1832051_image2_1.JPG"
      |}}
      |""".stripMargin

}


