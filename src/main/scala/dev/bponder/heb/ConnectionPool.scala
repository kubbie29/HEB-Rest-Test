package dev.bponder.heb

import doobie.util.transactor.Transactor
import org.h2.jdbcx.JdbcDataSource
import zio._
import zio.interop.catz._

import javax.sql.DataSource

/**
  * Typically, you would use a Connection Pool like HikariCP. Here, we're just gonna use the JDBC H2 datasource directly.
  * Don't do that in production !
  */
object ConnectionPool {

  def makeTransactor(config: Conf): Transactor[Task] =
    Transactor.fromDriverManager[Task](
      "org.postgresql.Driver",
      config.db.url,
      config.db.username,
      config.db.password
    )

  val ConnectorsDBTransactorLayer: ZLayer[Conf, Throwable, Transactor[Task]] =
    ZLayer.fromZIO(ZIO.serviceWithZIO[Conf] { conf =>
      ZIO.attemptBlocking {
        makeTransactor(conf)
      }
    })

  val live: ZLayer[Conf, Throwable, DataSource] = ZLayer.fromZIO(
    ZIO.serviceWithZIO[Conf] { conf =>
      ZIO.attemptBlocking {
        val ds = new JdbcDataSource
        ds.setURL(conf.db.url)
        ds.setUser(conf.db.username)
        ds.setPassword(conf.db.password)
        ds
      }
    }
  )

}
