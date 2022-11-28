package dev.bponder.heb.api

import dev.bponder.heb.Conf
import dev.bponder.heb.repository.ImageRepository
import io.github.gaelrenoux.tranzactio.{DbException, ErrorStrategiesRef}
import io.github.gaelrenoux.tranzactio.anorm.Database
import io.github.gaelrenoux.tranzactio.doobie.Connection
import zio.http._
import zio.http.model.Method
import zio._

/***
  * API Specification



  */
object ImagesApp {
  val app: Http[ImageRepository with Connection, DbException, Request, Response] =
    Http.collectHttp[Request] {

      case Method.GET -> !! / "text" =>
        val result = for {
          count <- ImageRepository.getImage
        } yield Response.text(s"Hello World! $count")
        ZIO.serviceWithZIO[Conf] { conf =>
          // if this implicit is not provided, tranzactio will use Conf.dbRecovery instead
          implicit val errorRecovery: ErrorStrategiesRef = conf.alternateDbRecovery
          Database.transactionOrWiden(result)
        }
        Http.fromZIO(result)
      case Method.GET -> !! / "json" => Response.json("""{"greetings": "Hello World!"}""").toHttp
    }
//  def apply(): Http[Any, Nothing, Request, Response] =
//    Http.collect[Request] {
//
//      /**
//        * GET /images?objects="dog,cat"
//        * Returns a HTTP 200 OK with a JSON response body containing only images that have the detected objects specified in the query parameter.
//        */
//      case req @ Method.GET -> _ / "images" if req.url.queryParams.nonEmpty =>
//        Response.text(s"Hello ${req.url.queryParams("objects").mkString(" and ")}!")
//
//      /**
//        * GET /images
//        * Returns HTTP 200 OK with a JSON response containing all image metadata.
//        */
////      case Method.GET -> _ / "images" =>
////        http.Response(
////          status = Status.Ok,
////          headers = Headers.empty,
////          body = Body.from
////        )
//      // GET /greet/:name
//      /**
//        * GET /images/{imageId}
//        * Returns HTTP 200 OK with a JSON response containing image metadata for the specified image.
//        */
//      case Method.GET -> _ / "greet" / imageId =>
//        Response.text(s"Hello $imageId!")
//
//      /**
//        * POST /images
//        * Send a JSON request body including an image file or URL, an optional label for the image, and an optional field to enable object detection.
//        * Returns a HTTP 200 OK with a JSON response body including the image data, its label (generate one if the user did not provide it), its
//        * identifier provided by the persistent data store, and any objects detected (if object detection was enabled).
//        */
//      //case Method.POST -> _ =>
//
//    }
}
