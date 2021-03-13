import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

//TODO: Decide if I should make the !uno command a requirement when playing (doesn't make much sense to since you can't see how many cards the person has)
//Could make it so when you say Uno, it checks to see if you actually do and then announces that that person has Uno?  (Most likely what's gonna happen)
//Or make it so after you play a card it announces how many cards you have?  (I like this idea)

//TODO: Rework this, I don't remember how any of it works and would probably help to change it up a bit.

//TODO: if it has MessageReceivedEvent event as a parameter, it needs to be moved to Commands.java and redone in here.

//TODO: figure out timed turns, like 30 seconds to a turn, if you don't play, you draw a card.
//TODO: Start game has a 1 minute timer, otherwise parameters get reset.

public class Game {

    static Deck deck = new Deck();

    static Player[] players = new Player[4];

    static int Players = 0;

    static int turn = 0;
    static int playDirection = 1;  //This will either be 1 or -1 for turn direction.  Reverse changes the value.

    static Card prevCard = new Card();

    //Wild Card colors
    static String[] colors = {"blue", "red", "yellow", "green"};

    static boolean gameCreationInProgress = false;
    static boolean gameInProgress = false;

    Timer timer = new Timer();


    public static void sendPrivateMessage(User user, String content) {
        // openPrivateChannel provides a RestAction<PrivateChannel>
        // which means it supplies you with the resulting channel
        user.openPrivateChannel().queue((channel) ->
        {
            channel.sendMessage(content).queue();
        });
    }

    public static void gameCreation() {

        TimerTask task = new TimerTask() {

            @Override
            public void run() {

                gameCreationInProgress = false;

            }

        };

    }

    public static void startGame() {

        //So I think to do this differently, we're just gonna use it to deal hands, play first card and use it like turn() but without all the checks.
        //So like, instead of running this at !start, just use addPlayer() when !start is run, and again when !join is played, and have !start and !join really do the same thing.


    }

    public static void addPlayer(User user) {

        //We check for duplicate player now.
        for (int i = 0; i < Players; i++) {

            if (players[i].user == user)
                return;

        }

        players[Players] = new Player();

        players[Players].user = user;
        players[Players].ID = user.getId();
        players[Players].playerName = user.getName();

        Players++;

    }

    //TODO: rework this
    /*public static void startGame(MessageReceivedEvent event) {

        //Check for need for computer?

        //First things first let's get our player list shuffled (I think we might get rid of this)
        List<Player> shufflePlayers = Arrays.asList(players);
        Collections.shuffle(shufflePlayers);
        shufflePlayers.toArray(players);

        //Giving player's cards
        for (int i = 0; i < 4; i++) {

            System.out.println("Giving " + players[i].playerName + " " + players[i].cards +"  cards");

            if (!players[i].isComputer) {
                sendPrivateMessage(players[i].user, players[i].handEmotes);
            }

            System.out.println(players[i].handEmotes);


        }

        //Deal the first card
        event.getChannel().sendMessage("First Card:").queue();
        event.getChannel().sendMessage(deck.deck[deck.cards - 1].emote).queue();
        prevCard = deck.deck[deck.cards - 1];

        //Check for wild card and then randomly select starting color
        if (prevCard.isWildCard) {

            Random rand = new Random();
            prevCard.color = colors[rand.nextInt(colors.length)];
            event.getChannel().sendMessage("The first card is a Wild Card!\nSo the first color will be: " + prevCard.color).queue();

            if (prevCard.cardName.equals("Plus 4")) {
                event.getChannel().sendMessage("AND it's a Plus 4!  Bad luck for " + players[turn].playerTag).queue();
                //Dealer.giveCard(players[turn], deck, 4);
                turn += playDirection;
                event.getChannel().sendMessage(players[turn].playerTag + ", it's your turn!").queue();

            }

        } else {

            event.getChannel().sendMessage(players[turn].playerTag + ", it's your turn!").queue();

        }

    }*/

    public static boolean checkMove(String color, int value, String effect, User user, MessageReceivedEvent event) {

        //Well first things first, gotta make sure your dumb ass actually has the card you wanna play.

        //but before that make sure it's your damn turn ya fuck
        if (!user.getId().equalsIgnoreCase(players[turn].ID)) {
            event.getChannel().sendMessage("It's not your turn <@" + event.getAuthor().getId() + ">!").queue();
            return false;
        }

        //Now we run a loop to check all cards in hand to see if the card player wants to play is actually there
        boolean cardExists = false;
        for (int i =0; i < players[turn].cards; i++) {

            if (players[turn].hand[i].color.equalsIgnoreCase(color)) {

                if (players[turn].hand[i].value == value  || players[turn].hand[i].specialType.equalsIgnoreCase(effect)) {

                    cardExists = true;
                    break;

                }

            }

        }

        //So if after all that, cardExists is still false, obviously the card doesn't exist so we exit out
        if (!cardExists) {
            event.getChannel().sendMessage("<@" + event.getAuthor().getId() + ">, you don't have that card!").queue();
            return false;
        }

        //Can go ahead and pass all wild cards
        if (color.equalsIgnoreCase("Wild"))
            return true;

        //So we want to check 3 things here

        //And now we work our way down that list, let's start with colors since that'll probably take care of it
        //Basically, if the colors match, we don't need to continue, we already know it'll work
        if (prevCard.color.equalsIgnoreCase(color))
            return true;

        //Won't check if effect is the same if previous card wasn't an effect card
        if (prevCard.isSpecialType) {

            if (prevCard.specialType.equalsIgnoreCase(effect))
                return true;  //We return true automatically here because code wouldn't make it this far if color didn't match.

        }

        //Ok now that smart asses are out the fuckin' window, we check for matching number
        if (prevCard.value == value)
            return true;


        event.getChannel().sendMessage("Invalid play! Try again.").queue();
        return false;

    }

    public static void drawCard(Player player, int cards) {

        //Okay, so let's start by telling the Player class that our player has another card.
        player.cards++;

        //Now lets get that card
        player.hand[player.cards] = deck.drawCard();

        //Ok so player now has card...I think that's it.

    }

    public static void turnProgression() {

        //First things first
        //Check for reverse card

        if (prevCard.specialType.equals("Reverse"))
            playDirection = playDirection * ( -1 );

        turn += playDirection;

        //Getting things ready for card effects
        if (turn > 4)
            turn = 0;

        if (turn < 0)
            turn = 4;

        //OOOOOOOO-KAY so let's start with card effects
        if (prevCard.isSpecialType) {

            switch (prevCard.specialType) {

                case "Skip":
                    turn += playDirection;
                    break;

                case "Plus 2":
                    drawCard(players[turn], 2);
                    turn += playDirection;
                    break;

                case "Plus 4":
                    drawCard(players[turn], 4);
                    turn += playDirection;
                    break;

            }

        }

        //Gotta do this again just in case a skip/plus 2/plus 4 was played and
        if (turn > 4)
            turn = 0;

        if (turn < 0)
            turn = 4;

        //And NOW we go ahead and tell next person it's their turn.


    }

    //TODO: move Dealer.giveCards over here to deal initial hand because I already made drawCard() and that makes that useless.
    //TODO: tbh could just put this at the end of start game...
    public static void dealHands() {

        for (int i = 0; i < 4; i++) {

            drawCard(players[i], 7);

        }

    }

}
