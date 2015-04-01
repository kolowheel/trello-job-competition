package org.ua.trello

import java.util.concurrent.Executors

import org.scalatest.FlatSpec

import scala.collection.immutable.Iterable
import scala.concurrent.duration.Duration
import scala.concurrent.{ExecutionContext, Await, Future}


/**
 * @author yaroslav.gryniuk
 */
class HashTest extends FlatSpec {
  "Hash" should "be compute properly" in {


    val ethalon = new DataProvider data
    implicit val executionContext = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(3))


    val res: Iterable[Boolean] = Await.result(Future.sequence(ethalon map {
      case (str, hash) =>
        Future {
          Hash.hash(str)(Hash.letters, Hash.startHash) == hash
        }
    }), Duration.Inf)




    assertResult(res.count((x) => x))(ethalon.size)


  }

}
