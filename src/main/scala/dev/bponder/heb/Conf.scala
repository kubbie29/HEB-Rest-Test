package dev.bponder.heb

import dev.bponder.heb.Conf.DbConf
import io.github.gaelrenoux.tranzactio.ErrorStrategies
import zio.{Layer, ZLayer, _}

case class Conf(
    db: DbConf,
    dbRecovery: ErrorStrategies,
    alternateDbRecovery: ErrorStrategies
)

object Conf {

  case class DbConf(
      url: String,
      username: String,
      password: String
  )

  def live: Layer[Nothing, Conf] = ZLayer.succeed(
    Conf(
      db = DbConf(s"jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres"),
      dbRecovery = ErrorStrategies.timeout(10.seconds).retryForeverExponential(10.seconds, maxDelay = 10.seconds),
      alternateDbRecovery = ErrorStrategies.timeout(10.seconds).retryCountFixed(3, 3.seconds)
    )
  )

}
