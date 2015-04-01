package org.ua.trello

/**
 * @author yaroslav.gryniuk
 */
object Hash {
  implicit val letters = "acdegilmnoprstuw"
  implicit val startHash: Long = 7

  def hash(s: String)(implicit letters: String, salt: Long) = s.foldLeft(salt: Long) {
    (h, c) =>
      h * 37 + letters.indexOf(c)
  }
}
