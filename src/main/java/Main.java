/*

Hello and welcome to my disaster!

 */

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

import java.io.FileNotFoundException;

import static net.dv8tion.jda.api.requests.GatewayIntent.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


//Revamp everything in here, kinda messy.
//Add admin controls to control things from discord.  Maybe also add admin controls for the console.
public class Main extends ListenerAdapter {

    public static void main(String[] args) throws LoginException, FileNotFoundException {

        File Token = new File("token.txt");

        Scanner scnr = new Scanner (Token);

        String token = scnr.nextLine();

        JDABuilder builder = JDABuilder.create(token, GUILD_MESSAGES);
        System.out.println("Token and permissions assigned.");

        builder.addEventListeners(new Main());
        System.out.println("Event Listeners Added");

        builder.build();
        System.out.println("We are go for launch!");

    }


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (!event.getAuthor().getId().equals("436627620705075200"))
            System.out.println( "We received a message from " + event.getAuthor().getName() + ": " + event.getMessage().getContentDisplay() );
        else
            System.out.println(event.getMessage().getContentDisplay());

        try {
            Commands.runCommand(event, event.getMessage().getContentDisplay());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
