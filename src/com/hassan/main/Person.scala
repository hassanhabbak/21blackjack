package com.hassan.main

import scala.collection.mutable.ListBuffer

/**
  * Class defining a person in the game
  */
abstract class Person {

  def getName : String

  var status = Person.playing

  protected var hand: ListBuffer[Card] = ListBuffer()

  /* Adds card to hand and returns score */

  def addCard(card: Card): Int ={
    hand += card
    val points = getHandValue
    if (points == 21)
      status = Person.won
    else if (points > 21)
      status = Person.busted

    status
  }

  def removeCard(card: Card): Unit ={
    hand -= card
  }

  /* Get status */

  def getStatus: Int ={
    status
  }

  /* Get hand cound */

  def getHandCount: Int ={
    hand.length
  }

  /* Gets hand value */

  def getHandValue: Int ={
    var points = 0
    var aceCount = 0
    for (card <- hand){
      if (card.rank == Ace){
        aceCount += 1
      }
      points += card.rank.value
    }

    // Get ace higher value if possible
    for (i <- 1 to aceCount) {
      if (10 + points < 21) {
        points += 10
      }
    }
    points
  }

  /* Gets a string of all held hands */

  def showCards (dealer: Boolean = false): String =
    if (dealer) s"${hand.head.rank.value} X" else hand.map(c => c.rank.value) mkString ", "

  /* Checks if player Can split
    if the presented card exists twice in hand */

  def canSplit(card: Card): Boolean = {
    var count = 0
    for (cardInHand <- hand if card.rank == cardInHand.rank) {
      count += 1
      if(count == 2)
        return true
    }
    false
  }


}

object Person {
  def playing: Int = 1

  def standing: Int = 2

  def busted: Int = 3

  def won: Int = 4
}