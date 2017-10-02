package com.hassan.main

/**
  * Class defining a player in the game
  */
class Player(val name: String) extends Person {

  private var bet: Int = 0

  override def getName : String = {
    "Player "+name
  }

  def setBet(betAmount:Int): Unit ={
    bet = betAmount
  }

  def getBet: Int = {
    bet
  }
}

object Player {
  def playing: Int = 1

  def standing: Int = 2

  def busted: Int = 3
}