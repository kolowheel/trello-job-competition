import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.{Executors, TimeUnit}
import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration


/**
 * @author yaroslav.gryniuk
 */
object MainParallel {

  def main(args: Array[String]) = {
    implicit val letters = "acdegilmnoprstuw"
    implicit val startHash: Long = 7
    val hashh = 956446786872726l
    val size = 9

    val counter: AtomicInteger = new AtomicInteger(0)
    findStringByHash(hashh, size, letters,  counter)

  }

  def findStringByHash(hash: Long, size: Int, source: String, counter: AtomicInteger) = {
    val current = System.currentTimeMillis
    def start: (Int, Array[Char], Long) => Boolean = {
      (pos, str, curHash) =>
        var flag = false
        if (pos == size) {
          if (curHash == hash) {
            flag = true
            println(System.currentTimeMillis - current)
            println(str.mkString)

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

    var list: collection.mutable.MutableList[Future[Any]] = collection.mutable.MutableList()
    source foreach {
      c =>
        list += future {
          val str = new Array[Char](size)
          str(0) = c
          start(1, str, 7 * 37 + source.indexOf(c))
        }
    }
    list foreach {
      c =>
        Await.ready(c, Duration.Inf)
    }

  }

  def hash(s: String)(implicit letters: String, salt: Long) =
    s.foldLeft(salt: Long) {
      (h, c) =>
        h * 37 + letters.indexOf(c)
    }


}

