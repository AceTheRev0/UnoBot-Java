import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

public class Admin {

    static public void commands(MessageReceivedEvent event, String input) {

        Scanner scnr = new Scanner(input);

        String command = scnr.next();

        switch (command) {



        }


    }

}
