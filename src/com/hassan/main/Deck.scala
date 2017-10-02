package com.hassan.main

/**
  * Class defining a deck in the game
  */
class Deck (numOfDecks:Int){

  //val cards = collection.mutable.ListBuffer() ++ scala.util.Random.shuffle(initDeck)
  private var cards = buildCards()

  /* Build Decks based on decks in the game */

  def buildCards():List[Card] ={
    var cards = List[Card]()
    for (i <- 1 to numOfDecks){
      cards = cards ++ scala.util.Random.shuffle(initDeck)
    }
    cards
  }

  /* Initialize Cards in a single deck */

  def initDeck =
    for {
      suit <- List(Heart, Diamond, Spade, Club)
      rank <- List(King, Queen, Jack, Ten, Nine, Eight, Seven, Six, Five, Four, Three, Two, Ace)
    }
      yield new Card(suit, rank)

  /* Deals a card from top of the deck */

  def dealCard: Card = {
    val card = cards.head
    cards = cards.drop(1)
    card
  }

  /* Returns size of Deck */

  def cardsInDeck: Int = {
    cards.length
  }

}

/* Represents the card Suit */

sealed trait Suit
case object Heart extends Suit
case object Diamond extends Suit
case object Spade extends Suit
case object Club extends Suit

/* Represents the card Rank */

sealed abstract class Rank(val value: Int)
case object King extends Rank(3)
case object Queen extends Rank(2)
case object Jack extends Rank(1)
case object Ten extends Rank(10)
case object Nine extends Rank(9)
case object Eight extends Rank(8)
case object Seven extends Rank(7)
case object Six extends Rank(6)
case object Five extends Rank(5)
case object Four extends Rank(4)
case object Three extends Rank(3)
case object Two extends Rank(2)
case object Ace extends Rank(1)

/* Represents a card */

class Card(suitc: Suit, rankc: Rank) {
  val rank = rankc
  val suit = suitc
}
