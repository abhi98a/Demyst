import zio.{App, ExitCode, Task, ZIO}
import zio.console.Console

object Main extends App {

  def run(args: List[String]): ZIO[zio.ZEnv, Nothing, ExitCode] =
    program
      .foldM(
        error => putStrLn(s"Error: ${error.getMessage}").as(ExitCode.failure),
        _ => ZIO.succeed(ExitCode.success)
      )

  def program: ZIO[Console, Throwable, Unit] =
    for {
      ip <- fetchIPAddress
      _  <- putStrLn(s"IP address: $ip")
    } yield ()

  def fetchIPAddress: Task[String] =
    Task.effectTotal("https://api.ipify.org/?format=json")
      .bracket(_ => Task.effectTotal(io.Source.fromURL(_)), source => Task.effectTotal(source.close()))
      .flatMap(source => Task.effectTotal(source.mkString))
      .map(parseIPAddress)

  def parseIPAddress(json: String): String =
    io.circe.parser.parse(json)
      .flatMap(_.hcursor.get[String]("ip"))
      .getOrElse("Unknown")

  def putStrLn(line: String): ZIO[Console, Nothing, Unit] =
    ZIO.accessM(_.get.putStrLn(line))
}
