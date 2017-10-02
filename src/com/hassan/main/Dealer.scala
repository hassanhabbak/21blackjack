package com.hassan.main

/**
  * Class defining a dealer in the game
  */
class Dealer extends Person {

  override def getName : String = {
    "* Bank * "
  }

  override def canSplit(card: Card): Boolean = {
    false
  }
}

object Dealer {
  def playing: Int = 1

  def standing: Int = 2

  def busted: Int = 3
}