import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

import java.util.Scanner;

public class Main extends ListenerAdapter {

    public static void main(String[] args) throws LoginException {

        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = "NDM2NjI3NjIwNzA1MDc1MjAw.XlQOwA.nTPsphhXP4yJZtrB2_EHqk18ar0";
        builder.setToken(token);

        builder.addEventListeners(new Main());

        builder.build();

    }

    String adminID = "110206264855769088";

    //Booleans for running shit
    static boolean gameCreation = false;
    static boolean inProgress = false;

    static int test = 0;


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {


        System.out.println( "We received a message from " + event.getAuthor().getName() + ": " + event.getMessage().getContentDisplay() );


        /*UNO!*/

        Scanner scnr = new Scanner(event.getMessage().getContentRaw());

        //We're gonna use Scanner to find our commands and extra shit
        String command = null;

        command = scnr.next();  //Gets our command

        /*To prevent bot from derping*/
        if (event.getAuthor().isBot()) {

            return;

        }

        /*Helpful commands*/
        if (command.equals("!help")) {

            Game.help(event);

        }

        if (command.equals("!rules")) {

            Game.rules(event);

        }

        /*Starting Game*/
        if (command.equals("!start")) {

            if (!gameCreation && !inProgress) {


                Game.deck.buildDeck();
                Game.deck.shuffleDeck();

                event.getChannel().sendMessage("<@" + event.getAuthor().getId() + "> wants to play a game of *UNO!*  Type !join to play!").queue();
                Game.addPlayer(event);
                gameCreation = true;
                return;

            } else if (gameCreation && !inProgress) {

                //Check for host perms
                for (int i = 0; i < Game.Players; i++) {

                    if (event.getAuthor().getId().equals(Game.players[i].ID) && Game.players[i].host) {

                        Game.startGame(event);
                        inProgress = true;

                    }

                }

            } else if (gameCreation || inProgress) {

                event.getChannel().sendMessage("A game is already in progress.").queue();

            }

        }

        if (command.equals("!join")) {

            if (gameCreation && !inProgress) {

                if (Game.Players < 4) {

                    Game.addPlayer(event);

                } else {

                    event.getChannel().sendMessage("Game is full, wait for the next one.  Or ask an admin to reset the game.").queue();

                }

            } else {

                event.getChannel().sendMessage("<@" + event.getAuthor().getId() + "> there isn't a game in progress.  To start one, type !start.").queue();

            }

        }

        /*Playing the game*/

        if (inProgress) {

            /*Playing a card*/
            if (command.equals("!play")) {

                if (Game.players[Game.turn].ID.equals(event.getAuthor().getId())) {  //Checks to see if it's the players turn

                    String mod = scnr.nextLine();
                    Scanner checkForInt = new Scanner(mod);

                    try {

                        Integer.parseInt(checkForInt.next());

                    } catch (NumberFormatException ex) {

                        event.getChannel().sendMessage("Input after !play must be a number!").queue();

                    }



                    Game.Turn(event, mod);

                } else {

                    event.getChannel().sendMessage("Wait your turn, <@" + event.getAuthor().getId() + ">").queue();

                }

            }

            //Drawing a card
            if (command.equals("!draw")) {

                Dealer.giveCard(Game.players[Game.turn], Game.deck, 1);
                Game.turn += Game.playDirection;

                if (Game.turn >= 4) {

                    Game.turn = 0;

                }

            }

        }


        /*Admin commands*/

        if (event.getAuthor().getId().equals(adminID)) {

            if (command.equals("!test")) {

                Deck testDeck = new Deck();
                testDeck.buildDeck();
                testDeck.shuffleDeck();

                Player testDummy = new Player();
                testDummy.user = event.getAuthor();

                switch (test) {

                    case 0:
                        Dealer.giveCard(testDummy, testDeck, 7);
                        Game.sendPrivateMessage(testDummy.user, testDummy.handEmotes);
                        test++;
                        break;

                    case 1:
                        event.getChannel().sendMessage(testDummy.hand[0].emote).queue();
                        break;

                }


                event.getChannel().sendMessage("test").queue();

            }

            if (command.equals("!reset")) {

                inProgress = false;
                gameCreation = false;

            }


        }

    }

}
