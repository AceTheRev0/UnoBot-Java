import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {

    static Card[] deck = new Card[108];

    int cards = 108;

    public static void buildDeck() {

        String[] emotes = {":red0:679002746581418034",":red1:679002732019056669",":red1:679002732019056669",":red2:679002715824717865",":red2:679002715824717865",":red3:679002703875145742",":red3:679002703875145742",":red4:679002689560117289",":red4:679002689560117289",":red5:679002674422611978",":red5:679002674422611978",":red6:679002657138016297",":red6:679002657138016297",":red7:679002645830303782",":red7:679002645830303782",":red8:679002632362393612",":red8:679002632362393612",":red9:679002620312158211",":red9:679002620312158211",":redrvs:679002573629292575",":redrvs:679002573629292575",":redp2:679002604810010634",":redp2:679002604810010634",":redskp:679002562774695957",":redskp:679002562774695957",":yellow0:679002552246861834",":yellow1:679002542591705138",":yellow1:679002542591705138",":yellow2:679002531527131147",":yellow2:679002531527131147",":yellow3:679002519719903237",":yellow3:679002519719903237",":yellow4:679002509725138985",":yellow4:679002509725138985",":yellow5:679002496856883200",":yellow5:679002496856883200",":yellow6:679002486299688993",":yellow6:679002486299688993",":yellow7:679002471800111176",":yellow7:679002471800111176",":yellow8:679002461989765170",":yellow8:679002461989765170",":yellow9:679002452531609612",":yellow9:679002452531609612",":yellowrvs:679002345631121470",":yellowrvs:679002345631121470",":yellowp2:679002355357712405",":yellowp2:679002355357712405",":yellowskp:679002334805753857",":yellowskp:679002334805753857",":blue0:679003318890135564",":blue1:679003306936500231",":blue1:679003306936500231",":blue2:679003295565479961",":blue2:679003295565479961",":blue3:679003282882166815",":blue3:679003282882166815",":blue4:679003238380470292",":blue4:679003238380470292",":blue5:679003221544534024",":blue5:679003221544534024",":blue6:679003207116128269",":blue6:679003207116128269",":blue7:679003190745890817",":blue7:679003190745890817",":blue8:679003174459146260",":blue8:679003174459146260",":blue9:679003159984603149",":blue9:679003159984603149",":bluervs:679003126430171166",":bluervs:679003126430171166",":bluep2:679003140850188329",":bluep2:679003140850188329",":blueskp:679003110433357835",":blueskp:679003110433357835",":green0:679003042347221042",":green1:679003016753578036",":green1:679003016753578036",":green2:679003001486311432",":green2:679003001486311432",":green3:679002985581510656",":green3:679002985581510656",":green4:679002968561025024",":green4:679002968561025024",":green5:679002953750675473",":green5:679002953750675473",":green6:679002907760263175",":green6:679002907760263175",":green7:679002881759641601",":green7:679002881759641601",":green8:679002862675689540",":green8:679002862675689540",":green9:679002849488797726",":green9:679002849488797726",":greenrvs:679002820393041930",":greenrvs:679002820393041930",":greenp2:679002834921848842",":greenp2:679002834921848842",":greenskp:679002801132535871",":greenskp:679002801132535871",":wild:679002325297135667",":wild:679002325297135667",":wild:679002325297135667",":wild:679002325297135667",":plus4:679002309153259554",":plus4:679002309153259554",":plus4:679002309153259554",":plus4:679002309153259554"};

        for (int k = 0; k < 108; k++)
            deck[k] = new Card();

        for ( int i = 0; i < 5; i++ ) {

            for ( int j = 0; j < 25; j++ ) {

                int place = j + ( 25 * i );

                //Switch to define color or Wild card
                switch(i) {

                    //Red
                    case 0:
                        deck[place].color = "Red";
                        deck[place].cardName = "Red";
                        break;

                    //Yellow
                    case 1:
                        deck[place].color = "Yellow";
                        deck[place].cardName = "Yellow";
                        break;

                    //Blue
                    case 2:
                        deck[place].color = "Blue";
                        deck[place].cardName = "Blue";
                        break;

                    //Green
                    case 3:
                        deck[place].color = "Green";
                        deck[place].cardName = "Green";
                        break;

                    //Wild cards
                    case 4:
                        deck[place].isWildCard = true;
                        break;

                }

                //Defining card values if they're not wild cards
                if (deck[place].isWildCard == false) {

                    if (j > 18) {

                        deck[place].isSpecialType = true;

                    } else {

                        deck[place].isSpecialType = false;

                    }

                    //If the card in question is a regular numerical value
                    if (j == 0) {

                        deck[place].value = j;

                    } else if (j % 2 == 0) {

                        deck[place].value = j / 2;

                    } else if (j % 2 !=0 ) {

                        deck[place].value = (j + 1) / 2;

                    }


                    switch (deck[place].value) {

                        case 0:

                        case 1:

                        case 2:

                        case 3:

                        case 4:

                        case 5:

                        case 6:

                        case 7:

                        case 8:

                        case 9:
                            deck[place].cardName = deck[place].cardName + " " + String.valueOf(deck[place].value);
                            deck[place].pointValue = deck[place].value;

                            break;

                        case 10:
                            deck[place].specialType = "Reverse";
                            deck[place].pointValue = 20;
                            deck[place].cardName = deck[place].cardName + " " + deck[place].specialType;
                            break;

                        case 11:
                            deck[place].specialType = "Plus 2";
                            deck[place].pointValue = 20;
                            deck[place].cardName = deck[place].cardName + " " + deck[place].specialType;
                            break;

                        case 12:
                            deck[place].specialType = "Skip";
                            deck[place].pointValue = 20;
                            deck[place].cardName = deck[place].cardName + " " + deck[place].specialType;
                            break;

                    }


                } else { //Defining Wild Card types

                    switch (j) {

                        case 0:

                        case 1:

                        case 2:

                        case 3:
                            deck[place].color = "Wild";
                            deck[place].wildCardType = "Wild";
                            deck[place].pointValue = 50;
                            deck[place].cardName = "Wild";
                            deck[place].value = 1;
                            break;

                        case 4:

                        case 5:

                        case 6:

                        case 7:
                            deck[place].color = "Wild";
                            deck[place].wildCardType = "Plus 4";
                            deck[place].pointValue = 50;
                            deck[place].cardName = "Plus 4";
                            deck[place].value = 2;
                            break;

                    }

                }

                //Terminates the for loop for j once Wild Cards are defined
                if ( i == 4 && j == 7 )
                    break;

            }

        }

        for (int k = 0; k < deck.length; k++)
            deck[k].emote = "<" + emotes[k] + ">";

    }

    public static void printDeck() {

        for (int i = 0; i < 108; i++) {

            //System.out.println("Card #:" + i + "\t" + deckName[i].cardName + "\t\t Value: " + deckName[i].value);
            System.out.printf("%d\t%d\t%s\t%b\t%s\t%b\t%s\t%s\t%s\n", deck[i].value, deck[i].pointValue, deck[i].color, deck[i].isSpecialType, deck[i].specialType, deck[i].isWildCard, deck[i].wildCardType, deck[i].cardName, deck[i].emote);

        }

    }

    public static Card drawCard() {

        Card card = new Card();

        Random rndm = new Random();

        int newCard = rndm.nextInt(108);

        boolean validCard = false;

        //We're gonna do a do-while loop because fuck you I'm an adult and I do what I want
        {

            if (!deck[newCard].isPlayed)
                validCard = true;

        } while (!validCard);

        card = deck[newCard];
        //remove card from deck
        deck[newCard].isPlayed = true;

        return card;

    }

    public static void putCardBack(Player player, Card card) {

        player.cards--;

        card.isPlayed = false;

        //TODO: make sure this is all that needs to be done.

    }

    //Value Card
    public static Card findCard(Player player, String color, int value) {

        Card card = new Card();

        for (int i = 0; i < 108; i++) {

            //Check color
            if (deck[i].color.equals(color)) {

                //Check value
                if (deck[i].value == value) {

                    //check isPlayed
                    if (deck[i].isPlayed) {

                        //define the card to make prevCard
                        card = deck[i];
                        //Readd card to deck
                        deck[i].isPlayed = false;

                        i = 108;

                    }

                }

            }

        }

        //Now to get the card out of player's hand.
        for (int j = 0; j < 108; j++) {

            if (player.hand[j].color.equals(color)) {

                //Check value
                if (player.hand[j].value == value) {

                    //define the card to make prevCard
                    player.hand[j] = new Card();  //Replace card with blank one so doesn't show in hand anymore.

                    j = 108;



                }

            }

        }

        return card;

    }

    //Effect/Wild Card
    public static Card findCard(Player player, String color, String specialType) {

        Card card = new Card();

        for (int i = 0; i < 108; i++) {

            //Check color
            if (deck[i].color.equals(color)) {

                //Check value
                if (deck[i].specialType.equals(specialType)) {

                    //check isPlayed
                    if (deck[i].isPlayed) {

                        //define the card to make prevCard
                        card = deck[i];
                        //Readd card to deck
                        deck[i].isPlayed = false;
                        i = 108;

                    }

                }

            }

        }

        return card;

    }

}
