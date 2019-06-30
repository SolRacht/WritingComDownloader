package scraper.util

import scala.util.Random

object RandomCoffee {
  val coffees: Seq[String] = Seq(
    "Turkish",
    "Antoccino",
    "Cafe Au Lait",
    "Iced, With a Plastic Umbrella",
    "Eiskaffee",
    "Black",
    "Grande Mocha Vanilla Bean Matcha Chai Latte Con Carne Deluxe",
    "Gas Station Special",
    "The coffee machine's broken. Somebody call Carol! Wait, she's on vacation! Who knows how to work this thing!?",
    "Toxic Sludge",
    "Coffee-In-A-Can",
    "What coffee?"
  )
  override def toString: String = Random.shuffle(coffees).head
}
