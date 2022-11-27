package dev.bponder.heb

import dev.bponder.heb.api._
import zhttp.service.Server
import zio._

object Main extends ZIOAppDefault {
  override def run: ZIO[Environment with ZIOAppArgs with Scope, Any, Any] =
    Server
      .start(
        port = 8080,
        http = ImagesApp()
      )
      .provide(
        // An layer responsible for storing the state of the `counterApp`
        ZLayer.fromZIO(Ref.make(0))
      )

}
