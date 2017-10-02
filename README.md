# 21blackjack

This is the a dutch card game similar to blackjack. 

# Rules are as follows:


Twenty-one is a game of chance. The chance of winning is based on the cards dealt by the program. The
game is similar to Blackjack, but differs in some areas, so please read this section carefully.
All players in the game place their bets after receiving their first card. Once everyone has placed their bets,
the participants get a second card.

One by one, the players of the game get the opportunity to play. Each play, the players have the option to
‘stand’ (hold your total and end your turn), ‘hit’ (ask for a card to bring your points as close as possible to
21), or perform special actions (here only ‘split’). If a player has more than 21 points in his hand, he is
‘bust’, and his bets are lost.

If all players are ready (stand or bust) the bank must play (only if there are players who are not bust). The
rules for the bank are simple: The bank must hit when it has a total of 16 points or less and must stand with
a total of 17 points or more. When the bank and player have the same number of points, the bank wins. If
the bank has more than 21 points, the bank is bust and all players that are standing, win.
When a player gets two identical cards, he can choose to ‘split’. This means that the cards are placed next
to each other on the table and the player can play twice, one game per card.

The number of points for the cards is as follows:
  • King 3 points, queen 2 points, jack 1 point.
  • Ace is 1 or 11 points of your choice.
  • Cards 2 to 10 have their normal point value.
  • The ‘suit’ of the card is not important.
  • The Joker does not play
  
Note that the game cards must be pre-shuffled and that you cannot have more than 3 players per deck (for
more players you will need more than one deck).

# Technical specs

The project is written in scala-sdk-2.12.3 with all the requirements in 21blackjack.iml

Unit tests are provided using scalatest_2.12:3.0.1 
