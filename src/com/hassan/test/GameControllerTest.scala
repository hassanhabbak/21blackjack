package com.hassan.test

import org.scalatest.FunSuite

import com.hassan.main._


/**
  * Created by hassanhabbak on 10/2/17.
  */
class GameControllerTest extends FunSuite {

  test("testPlayerWon") {
    val gc = new GameController
    gc.addPlayer("test")

    // Player wins if hand value bigger than dealer
    // Player loses if less than or equal dealer

    gc.dealer.addCard(new Card(Heart, Ace))
    gc.players.head.addCard(new Card(Heart, Two))

    assert(gc.playerWon(gc.players.head) === false)

    gc.players.head.addCard(new Card(Heart, Ten))

    assert(gc.playerWon(gc.players.head) === true)

    gc.dealer.addCard(new Card(Heart, Jack))

    assert(gc.playerWon(gc.players.head) === false)


  }

  test("testDealCard") {
    val gc = new GameController
    gc.addPlayer("test")

    val deck = gc.shuffleDeck(1)

    assert(deck.cardsInDeck === 52)

    gc.dealCard(deck, gc.players.head, faceUp = false)

    assert(deck.cardsInDeck === 51)
    assert(gc.players.head.getHandCount === 1)

  }

  test("testSplitPlayer") {
    val gc = new GameController
    gc.addPlayer("test")
    assert(gc.players.length === 1)

    val player = gc.players.head
    var card = new Card(Heart, Two)

    player.addCard(card)
    assert(gc.splitPlayer(player, new Card(Heart, Three)) === false)

    card = new Card(Heart, Three)
    player.addCard(card)

    card = new Card(Heart, Three)
    player.addCard(card)

    assert(gc.splitPlayer(player, card) === true)
    assert(gc.players.length === 2)
    assert(player.getHandCount === 2)
    assert(gc.players(1).getHandCount === 1)

  }

}
