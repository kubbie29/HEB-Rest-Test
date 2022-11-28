package dev.bponder.heb

import dev.bponder.heb.api._
import dev.bponder.heb.repository.ImageRepository
import doobie.Transactor
import io.github.gaelrenoux.tranzactio.doobie.Database
import zio._
import zio.http.ServerConfig.LeakDetectionLevel
import zio.http._

object Main extends ZIOAppDefault {

  private val conf            = Conf.live
  private val dbRecoveryConf  = conf >>> ZLayer.fromFunction((_: Conf).dbRecovery)
  private val datasource      = conf >>> (ConnectionPool.live ++ ConnectionPool.ConnectorsDBTransactorLayer)
  private val database        = (datasource ++ dbRecoveryConf) >>> Database.fromDatasourceAndErrorStrategies
  private val imageRepository = ImageRepository.live

  private val config = ServerConfig.default
    .port(8080)
    .maxThreads(8)
    .leakDetection(LeakDetectionLevel.DISABLED)
    .consolidateFlush(true)
    .flowControl(false)
    .objectAggregator(-1)

  private val configLayer = ServerConfig.live(config)

  type AppEnv = Database with ImageRepository with Conf
  private val appEnv = database ++ imageRepository ++ conf

  override def run: ZIO[ZIOAppArgs with Scope, Any, Any] =
    Server
      .serve(ImagesApp.app)
      .provide(
        ConnectionPool.ConnectorsDBTransactorLayer,
        configLayer,
        Server.live,
        appEnv
      )

}
