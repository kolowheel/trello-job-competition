package org.ua.trello

import java.util.concurrent.Executors
import org.scalatest.time.{Span, Millis}
import org.scalatest.time.SpanSugar

import org.scalatest.{FunSpec, FlatSpec}

import scala.collection.immutable.Iterable
import scala.concurrent.duration.Duration
import scala.concurrent.{ExecutionContext, Future, Await}
import scala.language.postfixOps

/**
 * @author yaroslav.gryniuk
 */
class SolverTest extends FlatSpec {
  "Hash" should "be found properly" in {
    val ethalon = new DataProvider data

    implicit val executionContext = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(3))
    val res: Iterable[Boolean] = Await.result(Future.sequence(ethalon map {
      case (str, hash) =>
        Future {
          Solver.solve(hash, str.length, Hash.letters) == str
        }
    }), Duration.Inf)
    assertResult(res.count((x) => x))(ethalon.size)
  }

}
