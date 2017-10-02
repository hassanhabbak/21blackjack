package com.hassan.main

import scala.collection.mutable.ListBuffer
import scala.io.StdIn._
import scala.util.Try
import Console.{GREEN, RED, RESET, YELLOW_B, BLUE}

/**
  * Game controller class, handles the rounds and game rules.
  */
class GameController {

  var players: ListBuffer[Player] = ListBuffer[Player]()
  val dealer = new Dealer

  def play() {
    Console.println(s"$RESET$GREEN-----GAME STARTED!!$RESET")

    /* We get all the names of the players in our round */

    var playerName = ""
    do{

      playerName = setPlayerName()

    } while( playerName != "" || players.isEmpty )

    println()

    /* Init deck */

    val numOfDecks = math.ceil(players.length/3.0).toInt
    val deck = shuffleDeck(numOfDecks)

    println("Number of decks "+numOfDecks+" with "+deck.cardsInDeck+" cards")
    println()

    /* hand over the first card */

    println(s"$RESET$GREEN-----Dealing first cards!!$RESET")

    for (player <- players if player.getStatus == Person.playing){
      dealCard(deck, player, faceUp = true)
    }
    dealCard(deck, dealer, faceUp = true)

    println()

    /* Get Bet from players */

    println(s"$RESET$GREEN-----Insert your bets!!$RESET")

    for (player <- players if player.getStatus == Person.playing){
      setBetFromPlayer(player)
    }

    println()

    /* We start the normal round where players play */

    println(s"$RESET$GREEN-----Player rounds!!$RESET")

    var roundActive = true
    for (player <- players if player.getStatus == Person.playing) {
      println(s"$RESET$BLUE--${player.getName}'s round ->$RESET")
      do { // Player round loop
        //round
        roundActive = playerAction(deck, player)
      } while (roundActive)
      println()
    }

    println()

    /* Dealer's turn to play */

    dealerRound(deck)

    println()

    /* Display win and losses */

    showPlayerWinLoss()
  }

  /* Set player name */

  def setPlayerName(): String = {
    Console.println(s"""$RESET${YELLOW_B}Please insert player name:$RESET""")
    var question = "Name: "
    if (players.nonEmpty)
      question = "(Enter empty to proceed) " + question
    val playerName = readLine(question)
    if (playerName != "") {
      addPlayer(playerName)
    }
    playerName
  }

  /* Add player */

  def addPlayer(playerName:String): Unit ={
    players += new Player(playerName)
    println(players)
    println(s"$RESET$GREEN" + playerName + " Added... " + players.length + s" players in game!$RESET")
  }

  /* Shuffle Deck */

  def shuffleDeck(numOfDecks:Int): Deck ={
    println(s"$RESET$GREEN--Shuffling Deck!!$RESET")
    new Deck(numOfDecks)
  }

  /* Player's round */

  def playerAction(deck: Deck, player: Player): Boolean ={
    val card = dealCard(deck, player, faceUp = true)
    var roundActive = false
    if (player.status == Person.busted) {
      println(s"$RESET $RED${player.getName} is BUST!$RESET")
      roundActive = false
    } else if (player.status == Person.won){
      println(s"$RESET $GREEN${player.getName} won with hand 21!$RESET")
      roundActive = false
    }else {
      var actionsDone = true
      var canSplit = player.canSplit(card)
      do { // Player action loop incase of multiple
        val action = getActionPlayer(player, canSplit)
        action match {
          case 1 => player.status = Person.standing; roundActive = false; actionsDone = true;
          case 2 => roundActive = true; actionsDone = true;
          case 3 => roundActive = splitPlayer(player, card); actionsDone = false; canSplit = false;
        }
      } while(!actionsDone)
    }
    roundActive
  }

  /* Dealer's round */

  def dealerRound(deck: Deck): Unit ={
    println(s"$RESET$GREEN--Bank's turn to play!!$RESET")
    while(dealer.getHandValue < 17){
      dealCard(deck, dealer, faceUp = true)
      if (dealer.status == Person.busted) {
        Console.println(s"$RESET$RED${dealer.getName} is BUST!$RESET")
      } else if (dealer.status == Person.won){
        println(s"$RESET$GREEN${dealer.getName} full with hand value of 21!$RESET")
      }
    }
  }

  /* Gets a string of card dealt */

  def showCard (card: Card, person: Person) : String =
    s"- ${card.rank} of ${card.suit} Was dealt to ${person.getName} - total points ${person.getHandValue}"

  /* Deal Card */

  def dealCard(deck: Deck, person: Person, faceUp: Boolean): Card ={
    val card = deck.dealCard
    person.addCard(card)
    if (faceUp)
      println(showCard(card, person))
    card
  }

  /* Gets the bet from a player */

  def setBetFromPlayer(player: Player): Unit ={
    var value = ""
    do {
      value = readLine(s"$RESET${YELLOW_B}Please insert bet value for ${player.getName}: $RESET")
      if (!Try(value.toInt).isSuccess)
        Console.println(s"$RESET$RED*** Please insert a valid positive number$RESET")
    } while (!Try(value.toInt).isSuccess)

    if (value.toInt == 0){
      // Sets status as busted if player does not bet
      player.status = Person.busted
      println(s"$RESET$RED${player.getName} is out of the game!$RESET")
    } else {
      player.setBet(value.toInt)
    }
  }

  /* Get Action from player */

  def getActionPlayer(player: Player, canSplit:Boolean): Int ={
    var action = GameController.unknown
    do {
      println(s"Commands available (stand, hit, split)")
      val value = readLine(s"$RESET${YELLOW_B}Please insert action ${player.getName}: $RESET")
      action = value match {
        case "stand" => GameController.stand
        case "hit" => GameController.hit
        case "split" => GameController.split
        case default => GameController.unknown
      }
      if (action == GameController.split && !canSplit){
        println(s"$RESET$RED No split action available$RESET")
        action = GameController.unknown
      }

      if (action == GameController.unknown){
        println(s"$RESET$RED Please enter a valid command!$RESET")
      }

    } while (action == GameController.unknown)

    action
  }

  /* show winners and losers */

  def showPlayerWinLoss(): Unit ={
    println(s"$RESET$YELLOW_B-*-*---> ${dealer.getName} hand of ${dealer.getHandValue}$RESET")
    for (player <- players){
      if (playerWon(player)){
        println(s"$RESET$GREEN-*-*---> ${player.getName} won bet of ${player.getBet} and hand of ${player.getHandValue}$RESET")
      } else {
        println(s"$RESET$RED-*-*---> ${player.getName} lost bet of ${player.getBet} and hand of ${player.getHandValue}$RESET")
      }
    }
  }

  /* Get if player won or lost */

  def playerWon(player: Player): Boolean ={
    if (player.getStatus == Person.busted)
      return false
    if (player.getHandValue > dealer.getHandValue || dealer.getStatus == Person.busted)
      return true
    false
  }

  /* Split player on card */

  def splitPlayer(player: Player, card: Card): Boolean ={
    if (!player.canSplit(card))
      return false
    val splitPlayer = new Player(player.getName+"_split")
    splitPlayer.addCard(card)
    splitPlayer.setBet(player.getBet)
    players += splitPlayer
    player.removeCard(card) // remove card from player
    println(s"$RESET$GREEN" + splitPlayer.getName + " Added... " + players.length + s" players in game!$RESET")
    true
  }


}

object GameController {
  def stand: Int = 1

  def hit: Int = 2

  def split: Int = 3

  def unknown: Int = 4
}