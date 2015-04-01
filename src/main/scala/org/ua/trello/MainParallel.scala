package org.ua.trello


/**
 * @author yaroslav.gryniuk
 */
object MainParallel {

  def main(args: Array[String]): Unit = {
    val s = System.currentTimeMillis
    println(Solver.
      solve(680131659347l, 7, Hash.letters))
    print("time " + (System.currentTimeMillis - s))

  }
}

