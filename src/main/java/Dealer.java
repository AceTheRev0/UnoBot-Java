//Could probably put this in Deck.java
public class Dealer {

    //TODO: If this is all Dealer does, move it to Game.java or Deck.java
    //TODO: Have this set d[card] = isPlayed instead of d.deck[card] = new Card();
    public static void giveCard(Player p, Deck d, int cards) {

        for (int i=0; i < cards; i++) {

            p.hand[p.cards] = new Card();

            p.hand[p.cards] = d.deck[ ( d.cards - 1 ) ];

            p.handEmotes += d.deck[ ( d.cards - 1 ) ].emote;

            d.deck[ ( d.cards - 1 ) ] = new Card();

            d.cards--;
            p.cards++;

        }

    }

}
