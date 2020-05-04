/**
 *
 */

public class Card {

    static int value;

    int pointValue;

    static String color;

    boolean isSpecialType;

    static String specialType;

    static boolean isWildCard;

    String wildCardType;

    String cardName;

    String emote;

    //Defining base values
    public Card() {

        value = 0;

        pointValue = 0;

        color = "";

        isSpecialType = false;

        specialType = "";

        isWildCard = false;

        wildCardType = "";

        cardName = "";

        emote = "";

    }

    public static void specialCard() {

        switch (specialType) {

            case "Reverse":
                Game.playDirection *= -1;
                break;

            case "Plus 2":
                int nextPlayer;

                if (Game.turn + Game.playDirection >= 4) {

                    nextPlayer = 0;

                } else if (Game.turn + Game.playDirection <= 0){

                    nextPlayer = 4;

                } else {

                    nextPlayer = Game.turn + Game.playDirection;

                }

                Dealer.giveCard(Game.players[nextPlayer], Game.deck, 2);
                break;

            case "Skip":
                if (Game.turn + Game.playDirection >= 4) {

                    Game.turn = 0;

                } else if (Game.turn + Game.playDirection <= 0){

                    Game.turn = 4;

                } else {

                    Game.turn = Game.turn + Game.playDirection;
                }
                break;

        }

    }

    public static boolean compCard(Card prevCard) {

        if (prevCard.color.equals(color)) { //Test for color first because of Wild Cards

            return true;

        } else if (prevCard.value == value) {

            return true;

        } else if (isWildCard) {

            return true;

        } else {

            return false;

        }

    }

}
