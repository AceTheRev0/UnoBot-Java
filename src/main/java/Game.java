import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.*;


public class Game {

    static Deck deck = new Deck();

    static Player[] players = new Player[4];

    static int Players = 0;

    static int turn = 0;
    static int playDirection = 1;  //This will either be 1 or -1 for turn direction.  Reverse changes the value.

    static Card prevCard = new Card();

    //Wild Card colors
    static String[] colors = {"blue", "red", "yellow", "green"};


    static public void sendPrivateMessage(User user, String content) {
        // openPrivateChannel provides a RestAction<PrivateChannel>
        // which means it supplies you with the resulting channel
        user.openPrivateChannel().queue((channel) ->
        {
            channel.sendMessage(content).queue();
        });
    }

    public static void help(MessageReceivedEvent event) {

        //TODO: Find a way to count characters

        String commands = "";

        try {
            File commandsFile = new File("C:\\Users\\deanh\\OneDrive - University of Florida\\UnoBot\\build\\libs\\commands.txt");
            Scanner reader = new Scanner(commandsFile);

            while (reader.hasNextLine()) {

                String line = reader.nextLine();

                if (line.length() + commands.length() >= 2000) {

                    sendPrivateMessage(event.getAuthor(), commands);
                    commands = line + "\n";

                } else {

                    commands += line + "\n";

                }

            }

            sendPrivateMessage(event.getAuthor(), commands);

            reader.close();

        } catch (FileNotFoundException e) {

            System.out.println("Commands.txt not found!");
            e.printStackTrace();

        }

    }

    public static void rules(MessageReceivedEvent event) {

        String rules = "";

        try {
            File rulesFile = new File("rules.txt");
            Scanner reader = new Scanner(rulesFile);

            while (reader.hasNextLine()) {

                String line = reader.nextLine();

                if (line.length() + rules.length() >= 2000) {

                    event.getChannel().sendMessage(rules).queue();
                    rules = line + "\n";

                } else {

                    rules += line + "\n";

                }

            }

            event.getChannel().sendMessage(rules).queue();

            reader.close();

        } catch (FileNotFoundException e) {

            System.out.println("rules.txt not found!");
            e.printStackTrace();

        }

    }

    public static void addPlayer(MessageReceivedEvent event) {

        //Testing for duplicate players

        boolean duplicatePlayer = false;

        for (int i = 0; i < Players; i++) {

            if (event.getAuthor().getId().equals(players[i].ID)) {

                duplicatePlayer = true;
                break;

            }

        }

        if (!duplicatePlayer) {

            //Creates new player
            players[Players] = new Player();
            players[Players].playerTag = "<@" + event.getAuthor().getId() + ">";
            players[Players].ID = event.getAuthor().getId();
            players[Players].playerName = event.getAuthor().getName();
            players[Players].user = event.getAuthor();
            Dealer.giveCard(players[Players], deck, 7);
            System.out.println(players[Players].handEmotes);
            if (Players == 0)
                players[Players].host = true;
            Players++;
            event.getChannel().sendMessage(players[Players-1].playerTag + " has joined the game! ("+Players+"/4)").queue();

        } else {

            event.getChannel().sendMessage("<@" + event.getAuthor().getId() + ">, you've already signed up to play!").queue();
            duplicatePlayer = false;

        }

        if (Players == 4) {

            event.getChannel().sendMessage("Lobby's full! Let's Play!").queue();
            startGame(event);
            Main.inProgress = true;

        }

    }

    public static void startGame(MessageReceivedEvent event) {

        //Adds computers if there's not enough players
        if (Players < 4) {

            for (; Players < 4; Players++) {

                int computer = 1;

                players[Players] = new Player();
                players[Players].isComputer = true;

                players[Players].playerTag = "Computer #" + computer;
                players[Players].ID = "";
                players[Players].playerName = "Computer #" + computer;
                players[Players].user = null;
                computer++;

            }

        }

        //First things first let's get our player list shuffled
        List<Player> shufflePlayers = Arrays.asList(players);
        Collections.shuffle(shufflePlayers);
        shufflePlayers.toArray(players);

        //Giving computer's cards
        for (int i = 0; i < 4; i++) {

            System.out.println("Giving " + players[i].playerName + " " + players[i].cards +"  cards");
            if (!players[i].isComputer) {
                sendPrivateMessage(players[i].user, players[i].handEmotes);
            } else {
                System.out.println(players[i].handEmotes);
            }

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
                Dealer.giveCard(players[turn], deck, 4);
                turn += playDirection;
                event.getChannel().sendMessage(players[turn].playerTag + ", it's your turn!").queue();

            }

        } else {

            event.getChannel().sendMessage(players[turn].playerTag + ", it's your turn!").queue();

        }

        /*if (players[turn].isComputer) {

            Turn(event, "");

        }*/

    }

    //TODO: Make this work
    public static void Turn(MessageReceivedEvent event, String Modifier) {

        Scanner modifier = new Scanner(Modifier);
        int card = modifier.nextInt() - 1;  //Gonna subtract 1 to make up for Arrays (fuck em right?)
        String color = null;

        //First: check for valid play
        System.out.println("Discard: " + prevCard.cardName + "\n" +
                           "Player:" + players[turn].hand[card].cardName);

        //So this basically runs through to find invalid plays.  If the play is valid, it runs through the if-statements without issue.
        if (prevCard.color.equals( players[turn].hand[card].color ) ) { //If the colors are the same

            if ( prevCard.isSpecialType && players[turn].hand[card].isSpecialType ) { //If both cards are "special type"

                if ( !prevCard.specialType.equals(players[turn].hand[card].specialType) ) { //If cards are not the same face

                    event.getChannel().sendMessage("Invalid play, cards must be the same color or have the same face value.").queue();

                }

            }

            if ( prevCard.value != players[turn].hand[card].value ) {  //If the cards aren't the same value



            }

        } else { //If colors aren't the same

            event.getChannel().sendMessage("Invalid play, cards must be the same color or have the same face value.").queue();

        }

    }

    public static void checkForEmptyDeck(Deck mainPile) {

        //Don't wanna find it when it's empty in case there's a +4 before
        if (mainPile.cards <= 10) {

            mainPile.buildDeck();
            mainPile.shuffleDeck();
            mainPile.cards = 108;

        }

    }

}
