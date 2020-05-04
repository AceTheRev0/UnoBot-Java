import net.dv8tion.jda.api.entities.User;

public class Player {

    /*Discord info*/
    //playerName matches discord name
    String playerName;

    //To tag player
    String playerTag;

    //For DM-ing
    User user;

    //ID to reference
    String ID;

    /*Game info*/
    //# of cards player has
    static int cards;

    //Player's hand
    static Card[] hand = new Card[108];

    //Has the player called Uno?
    boolean calledUno;

    boolean isComputer;

    boolean host;

    String handEmotes;

    //Assigns default values
    public Player() {

        playerName = "Undefined";

        playerTag = "Undefined";

        ID = "";

        cards = 0;

        calledUno = false;

        isComputer = false;

        host = false;

        handEmotes = "";

    }

}
