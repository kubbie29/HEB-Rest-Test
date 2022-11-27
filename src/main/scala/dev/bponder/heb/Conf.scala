package dev.bponder.heb

import dev.bponder.heb.Conf.DbConf
import zio.{Layer, ZLayer, _}

case class Conf(
    db: DbConf
)

object Conf {

  case class DbConf(
      url: String,
      username: String,
      password: String
  )

  def live: Layer[Nothing, Conf] = ZLayer.succeed(
    Conf(
      db = DbConf(s"jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres")
    )
  )

}
