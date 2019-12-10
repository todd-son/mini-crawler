package io.devsun.crawl
import io.circe._, io.circe.parser._

case class JsonItem(payload: String) extends Item {
  private val json =
    parse(payload)
      .getOrElse(throw new IllegalStateException(s"Couldn't parse JSON. JSON is $payload"))

  override def getString(path: String): Option[String] = {
    val paths = path.split("\\.")
    val cursor: ACursor = json.hcursor

    val result = paths.foldLeft(cursor) {
      case (cursor, p) => cursor.downField(p)
    }.as[String]

    result.toOption
  }
}

