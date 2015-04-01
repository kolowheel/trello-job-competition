package org.ua.trello

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import java.util.concurrent.atomic.AtomicInteger

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

/**
 * @author yaroslav.gryniuk
 */
object Solver {

  def solve(hash: Long, size: Int, source: String) = {
    def wrapperWithResult = {
      (pos: Int, str: Array[Char], curHash: Long) =>
        var result: Option[String] = None
        def start: (Int, Array[Char], Long) => Boolean = {
          (pos, str, curHash) =>
            var flag = false
            if (pos == size) {
              if (curHash == hash) {
                flag = true
                result = Some(str.mkString)
              }
            }
            else {
              source foreach {
                c =>
                  if (!flag) {
                    str(pos) = c
                    if (start(pos + 1, str, curHash * 37 + source.indexOf(c)))
                      flag = true
                  }
              }
            }
            flag
        }
        start(pos, str, curHash)
        result
    }

    var list: collection.mutable.MutableList[Future[Option[String]]] = collection.mutable.MutableList()
    source foreach {
      c =>
        list += Future {
          val str = new Array[Char](size)
          str(0) = c
          wrapperWithResult(1, str, 7 * 37 + source.indexOf(c))
        }
    }

    Await.ready(Future.find(list)((x: Option[String]) => x.isDefined), Duration.Inf).value.get.get.get.get
  }
}
