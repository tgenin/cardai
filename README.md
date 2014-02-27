cardai
======

Cardai is a game card engine.

Cardai allows developpers to implement sets of rules of different card games,
and then to design AI abled to play.

Cardai allows users to let AIs play together and analyse the results of many
hands played by them in order to evaluate which is the best selected AI.

A new game must extend org.cardai.Game and implement abstract methods

A new strategy must extend org.cardai.PlayingStrategy and implement abstract method 'play'

Currently, strategies must be labeled and manually put in the Map "strategies" in the setStrategies method of the corresponding Game.

Currently, only 'simple-game' is available with 4 strategies: 'first-card', 'random-card', 'highest-card' and 'first-improved'
