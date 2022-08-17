package io.devsun.crawl

import io.devsun.crawl.HtmlItemSpec.html
import org.scalatest.WordSpec

class HtmlItemSpec extends WordSpec {
  "HtmlItem" should {
    "extract by xpath" in {
      val htmlItem = HtmlItem(html)

      val summary = htmlItem.getString("(//td)[2]")
      val buy = htmlItem.getString("(//td)[3]")
      val target = htmlItem.getString("(//td)[4]")
      val current = htmlItem.getString("(//td)[5]")

      println(summary)
      println(target)
      println(current)
    }
  }
}

object HtmlItemSpec {
  val html =
    """
      |<td class="clf c"><span class="yy1">20</span><span class="yy2">22</span>/06/08</td>
 <html>
      | <head></head>
      | <body>
      |  <table>
      |   <tbody>
      |    <tr>
      |     <td class="clf c"><span class="yy1">20</span><span class="yy2">22</span>/06/08</td>
      |     <td class="l nopre">
      |      <dl class="um_tdinsm">
      |       <dt><a href="javascript:ViewReport('A026890');">스틱인베스트먼트 <span class="txt1">A026890</span></a><span class="txt2"> -경쟁력은 비 올 때 드러난다</span>
      |       </dt>
      |       <dd>
      |         현재 포트폴리오와 미래 경쟁력 고려할 필요
      |       </dd>
      |       <dd>
      |         포트폴리오가 잘 분산됐으며 여러 건 회수가 예정되어 실적 반등 예상
      |       </dd>
      |      </dl> </td>
      |     <td class="c nopre2"><span class="gpbox">&nbsp;<img src="../img/shbg.png" alt="신규" class="gp_img new">Not Rated</span></td>
      |     <td class="r nopre2"><span class="gpbox">&nbsp;</span></td>
      |     <td class="r">&nbsp;8,740</td>
      |     <td class="cle c nopre2"><span class="gpbox"><img src="../img/shbg.png" alt="베스트" class="gp_img ">한국투자증권<br>백두산</span></td>
      |    </tr>
      |   </tbody>
      |  </table>
      | </body>
      |</html>
      |""".stripMargin
}
