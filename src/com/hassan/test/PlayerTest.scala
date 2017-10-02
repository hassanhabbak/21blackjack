package com.hassan.test

import com.hassan.main._
import org.scalatest.FunSuite


/**
  * Created by hassanhabbak on 10/2/17.
  */
class PlayerTest extends FunSuite {

  test("testGetHandValue") {
    val player = new Player("Test")

    player.addCard(new Card(Heart, King)) // Value 3
    assert(player.getHandValue === 3)

    player.addCard(new Card(Heart, Two)) // Value 2
    assert(player.getHandValue === 5)

    player.addCard(new Card(Heart, Ace)) // Value 11 or 1
    assert(player.getHandValue === 16)

    player.addCard(new Card(Heart, Ten)) // Value 10
    assert(player.getHandValue === 16)

    assert(player.status == Person.playing)

    player.addCard(new Card(Heart, Ten)) // Value 10
    assert(player.getHandValue === 26)

    assert(player.status == Person.busted)

  }

  test("testCanSplit") {
    val player = new Player("Test")

    var card = new Card(Heart, King)
    player.addCard(card)

    card = new Card(Heart, Ace)
    player.addCard(card)
    assert(player.canSplit(new Card(Heart, Ten)) === false)

    card = new Card(Heart, Ace)
    player.addCard(card)
    assert(player.canSplit(card) === true)

    card = new Card(Heart, King)
    player.addCard(card)
    assert(player.canSplit(card) === true)

  }

}
