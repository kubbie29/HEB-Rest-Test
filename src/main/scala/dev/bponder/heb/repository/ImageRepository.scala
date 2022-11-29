package dev.bponder.heb.repository

import dev.bponder.heb.model.Image
import doobie.implicits._
import io.github.gaelrenoux.tranzactio.DbException
import io.github.gaelrenoux.tranzactio.doobie._
import zio._

object ImageRepository {

  trait Service {
    val getImage: TranzactIO[Int]
    def insertImage(img: Image): TranzactIO[Unit]
  }

  val live: ULayer[ImageRepository] = ZLayer.succeed(new Service {

    val getImage: TranzactIO[Int] = tzio {
      sql"""select count(*) from images""".query[Int].unique
    }

    override def insertImage(img: Image): TranzactIO[Unit] = tzio {
      sql"""insert into images
            | (image_id,image_label,image_data,objects_json,metadata)
            | values(uuid_generate_v4(),${img.imageLabel},${img.imageData},null,null)""".stripMargin.update.run
        .map(_ => ())
    }
  })

  def getImage: ZIO[ImageRepository with Connection, DbException, Int] =
    ZIO.serviceWithZIO[ImageRepository](_.getImage)

  def insertImage(img: Image): ZIO[ImageRepository with Connection, DbException, Unit] =
    ZIO.serviceWithZIO[ImageRepository](_.insertImage(img))
}
