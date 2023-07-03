import zio.test.Assertion._
import zio.test._

object MainSpec extends DefaultRunnableSpec {

  def spec: ZSpec[Environment, Failure] =
    suite("MainSpec")(
      testM("parse valid IP address from JSON") {
        val json = """{"ip": "192.0.2.1"}"""
        val result = Main.parseIPAddress(json)
        assertM(result)(equalTo("192.0.2.1"))
      },
      testM("parse invalid IP address from JSON") {
        val json = """{"ip": 12345}"""
        val result = Main.parseIPAddress(json)
        assertM(result)(equalTo("Unknown"))
      }
    )
}
