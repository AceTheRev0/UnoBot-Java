import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

public class Commands {

    //All our commands are gonna run from main to this method.
    static public void runCommand(MessageReceivedEvent event, String input) throws FileNotFoundException {



        //TODO: create a string 'command' that is input without the trigger

        char trigger = '!';  //Can make this any character you want

        //if input does not start with our trigger, command does not go through.
        if (input.charAt(0) != trigger) {

            return;

        }

        String commandInput = input.substring(1);
        Scanner scnr = new Scanner(commandInput);

        String command = scnr.next();

        /*TODO: Gonna do a switch here for commands and do methods for each command.
        *  Have the switch do an invalid command return in the default setting*/

        switch(command) {

            case "commands":

            case "help":
                File commands = new File("commands.txt");
                Scanner commandLine = new Scanner(commands);
                StringBuilder commandsMessage = new StringBuilder();

                //Let's try this again.
                while (commandLine.hasNextLine()) {

                    commandsMessage.append(commandLine.nextLine()).append("\n");

                }

                /*for (int i = 0; i < 5; i++) {

                    commandsMessage.append(commandLine.nextLine()).append("\n");

                }*/
                event.getChannel().sendMessage(commandsMessage.toString()).queue();
                break;

            //TODO: Game.startGame();
            case "start":
                if (!Game.gameCreationInProgress) {

                    event.getChannel().sendMessage("<@" + event.getAuthor().getId() + "> is trying to start a game of *Uno!*  Type **!join** to join in on the fun!").queue();

                    //TODO: 1 minute timer on this.
                    //TODO: Game.gameCreation()



                } else {



                }
                break;

            //TODO: Game.addPlayer();
            case "join":
                Game.addPlayer(event.getAuthor());
                break;

            //!play {color} {number}
            case "play":
                //Alright this is where shit kinda gets fucky.
                int value = -1;
                String effect = "0";
                String color;
                boolean isValue = false;
                boolean isEffect = false;

                if (scnr.next().equalsIgnoreCase("plus")) { //Plus 4

                    color = "Wild";
                    effect = "Plus 4";

                } else if (scnr.next().equalsIgnoreCase("wild")) { //Wild

                    color = "Wild";
                    effect = "Wild";

                } else {  //Literally everything else

                    color = scnr.next();

                    //checking if basic value card or string card (reverse, plus 2, skip)
                    if (scnr.hasNextInt()) {

                        value = scnr.nextInt();
                        isValue = true;

                    } else {

                        effect = scnr.next();
                        isEffect = true;

                    }

                }

                //So at this point we know what card has been played.  Now to check validity of play.
                if (!Game.checkMove(color, value, effect, event.getAuthor(), event)) {
                    return;
                }

                //If it makes it here, play is 100% valid, and we can re-add the card to the deck, and set prevCard to the one that's being played

                if (isValue)
                    Game.prevCard = Game.deck.findCard(Game.players[Game.turn], color, value);

                if (isEffect)
                    Game.prevCard = Game.deck.findCard(Game.players[Game.turn], color, effect);

                //So up to here, card has been set as prev.Card, play has been validated, so we can go ahead and progress turn!
                Game.turnProgression();

                //And that does all that, thank you for coming to my TED Talk!
                event.getChannel().sendMessage("<@" + Game.players[Game.turn].ID + ">, it's your turn!").queue();

                break;

            case "rules":
                File rules = new File("rules.txt");
                Scanner rule = new Scanner(rules);
                //TODO: this



                break;

            case "test":
                event.getChannel().sendMessage("Test successful!").queue();
                return;

            case "draw":

                break;

            case "admin":
                //Check for admin perms
                File adminIDs = new File("admin.txt");
                Scanner adminID = new Scanner(adminIDs);
                {

                    if (adminID.nextLine().equals(event.getAuthor().getId())) {

                        Admin.commands(event, scnr.nextLine());
                        return;

                    }

                } while (adminID.hasNext()); //This doesn't go past the first line, but for now it's just me so ¯\_(ツ)_/¯
            event.getChannel().sendMessage("You do not have admin permissions.").queue();
                break;

            default:

                break;

        }

    }

}
