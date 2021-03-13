import net.dv8tion.jda.api.entities.User;

public class Player {

    /*Discord info*/
    //playerName matches discord name
    static String playerName;

    //For DM-ing
    static User user;

    //ID to reference
    static String ID;

    /*Game info*/
    //# of cards player has
    static int cards;

    //Player's hand
    static Card[] hand = new Card[108];

    //Has the player called Uno?
    static boolean calledUno;

    static boolean isComputer;

    boolean host;

    String handEmotes;

    //Assigns default values
    public Player() {

        playerName = "Undefined";

        ID = "";

        cards = 0;

        calledUno = false;

        isComputer = false;

        host = false;

        handEmotes = "";

    }

    public static void importData() {

        playerName = user.getName();
        ID = user.getId();


    }

    public static int points() {

        int handValue = 0;

        for (int i = 0; i < cards; i++) {

            handValue += hand[i].pointValue;

        }

        return handValue;
    }

}
