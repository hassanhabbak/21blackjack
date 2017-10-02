package com.hassan.test

import com.hassan.main.{Rank, Deck}
import org.scalatest.FunSuite

/**
  * Created on 10/1/17.
  */
class DeckTest extends FunSuite {

  test("test InitDeck") {
    var deck = new Deck(1)
    assert(deck.cardsInDeck === 52)

    deck = new Deck(2)
    assert(deck.cardsInDeck === (52*2))
  }

  test("testDealCard") {
    val deck = new Deck(1)

    val card = deck.dealCard

    assert(card.rank !== 0)

    assert(deck.cardsInDeck === 51)
  }

  test("test all cards exist in deck") {
    val deck = new Deck(1)
    var cardCount = collection.mutable.Map[Rank, Int]()

    for(i <- 1 to 52){
      val card = deck.dealCard
      cardCount.update(card.rank, cardCount.getOrElse(card.rank, 0) + 1)
    }

    for((k,v) <- cardCount){
      assert(v === 4)
    }
  }

}
