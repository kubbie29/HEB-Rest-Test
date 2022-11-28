package dev.bponder.heb.repository

import doobie.implicits._
import io.github.gaelrenoux.tranzactio.DbException
import io.github.gaelrenoux.tranzactio.doobie._
import zio._

object ImageRepository {

  trait Service {
    val getImage: TranzactIO[Int]
  }

  val live: ULayer[ImageRepository] = ZLayer.succeed(new Service {

    val getImage: TranzactIO[Int] = tzio {
      sql"""select count(*) from images""".query[Int].unique
    }
  })

  def getImage: ZIO[ImageRepository with Connection, DbException, Int] =
    ZIO.serviceWithZIO[ImageRepository](_.getImage)
}
