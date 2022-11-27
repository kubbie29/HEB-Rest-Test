package dev.bponder.heb.repository

import doobie.Transactor
import zio._

trait ImageRepository {}

case class ImageRepositoryLive(xa: Transactor[Task]) {}
