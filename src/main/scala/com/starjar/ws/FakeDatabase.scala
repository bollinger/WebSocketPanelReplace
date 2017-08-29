package com.starjar.ws

import org.apache.wicket.protocol.ws.api.message.IWebSocketPushMessage

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.Promise
import scala.util.Random
import scala.util.Try

case class ResBroadcastMessage(result: Try[Res]) extends IWebSocketPushMessage

case class Res(path: String, childPaths: List[String])


object FakeDatabase {

  val r = new Random()

  def load(path: String): Promise[Res] = {
    println(s"Load path=$path")
    val promise = Promise[Res]

    promise.completeWith(
      Future[Res] {
        Thread.sleep(1000 + r.nextInt(1000)) // simulate a really slow database

        val children = if (path.length < 5) {
          "ABC".map{ path + _}.toList        // List(path + 'A', path + 'B', path + 'C')
        } else {
          List() // stop
        }

        Res(path, childPaths = children)
      }
    )

  }
}