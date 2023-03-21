/**
 * This class is the main class of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game. Users
 * can walk around some scenery. That's all. It should really be extended
 * to make it more interesting!
 * 
 * To play this game, create an instance of this class and call the "play"
 * method.
 * 
 * This main class creates and initialises all the others: it creates all
 * rooms, creates the parser and starts the game. It also evaluates and
 * executes the commands that the parser returns.
 * 
 * @author Marten Wilhelm
 * @version 1.0 (December 2022-2023)
 */

class Game {
    // ~~~ initialization ~~~
    public static void main(String args[]) {
        Game g = new Game();
        g.play();
    }

    private Parser parser;
    public static Room currentRoom;
    private Player player;
    public static Room roadside, freyasquare, infiniteforest, jormungandr;
    public Shop dwarvensmithy;
    private Weapon wooddenSword, commonersSword, Etheria, Durendal;
    private Food smallhealingPotion, largehealingPotion, boostPotion;

    private int Dubloons = 10;

    // text farben//
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";

    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        createRooms();
        createItems();
        parser = new Parser();
        player = new Player();
    }

    private void createItems() {
        wooddenSword = new Weapon(1, 2, 2, "useless woodden Sword");
        commonersSword = new Weapon(10, 5, 2, "Commoners Sword");
        Etheria = new Weapon(30, 15, 3, "Sword of Etheria");
        Durendal = new Weapon(50000, 90000000, 5, "The legendary Sword Durendal");
        smallhealingPotion = new Food(5, 5, 1, "Small healing Potion");
        largehealingPotion = new Food(10, 15, 1, "Large healing Potion");
        boostPotion = new Food(15, 20, 2, "boost Potion");
        dwarvensmithy.itemadd(wooddenSword);
        dwarvensmithy.itemadd(commonersSword);
        dwarvensmithy.itemadd(Etheria);
        dwarvensmithy.itemadd(Durendal);
        dwarvensmithy.itemadd(smallhealingPotion);
        dwarvensmithy.itemadd(largehealingPotion);
        dwarvensmithy.itemadd(boostPotion);
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms() {

        // create the rooms
        roadside = new Room("standing next to a signpost");
        freyasquare = new Room("entering the freyasquare");
        dwarvensmithy = new Shop("in the dwarvensmithy");
        infiniteforest = new Room("in the infinite forest, you are trapped!");
        jormungandr = new Room("fucked, the midgardsnake will eat you!?!");

        // initialise room exits
        roadside.setExits(jormungandr, freyasquare, infiniteforest, null, null);
        freyasquare.setExits(dwarvensmithy, null, null, roadside, null);
        dwarvensmithy.setExits(null, null, freyasquare, null, null);
        infiniteforest.setExits(infiniteforest, infiniteforest, infiniteforest, infiniteforest, roadside);
        jormungandr.setExits(null, null, roadside, null, null);
        currentRoom = roadside; // start game roadside
    }

    /**
     * Main play routine. Loops until end of play.
     */
    public void play() {
        printWelcome();

        // Enter the main command loop. Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        if (currentRoom == infiniteforest) {
            System.out.println("Congratulations you escaped the infinite forest by killing yourself, start again!");
        } else {
            System.out.println("Thank you for confronting those eldritch horrors.  Good bye.");
        }
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to Yggdrasil!");
        System.out.println("Yggdrassil is a world filled with wonder and excitement.");
        System.out.println("A world where you never know what might hit you next!");
        System.out.println("A dragon? A fairy? Or the pure embodiment of death?");
        System.out.println("Perhaps");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println("You are " + currentRoom.getDescription());
        System.out.print("Exits: ");
        if (currentRoom.northExit != null)
            System.out.print("north ");
        if (currentRoom.eastExit != null)
            System.out.print("east ");
        if (currentRoom.southExit != null)
            System.out.print("south ");
        if (currentRoom.westExit != null)
            System.out.print("west ");
        System.out.println();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
     */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        if (command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help"))
            printHelp();
        else if (commandWord.equals("go")) {
            goRoom(command);
        } else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        } else if (commandWord.equals("dubloons")) {
            System.out.println("You have " + Dubloons + " dubloons");
        } else if (commandWord.equals("shop")) {
            if (currentRoom == dwarvensmithy) {
                dwarvensmithy.accessShop();
            }
        } else if (commandWord.equals("purchase")) {
            if (currentRoom == dwarvensmithy) {
                purchase(command);
            }
        } else if (commandWord.equals("inventory")) {
            player.accessInventory();
        }

        return wantToQuit;

    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the
     * command words.
     */
    private void printHelp() {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around Yggdrasil.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println("   go; quit; help; inventory; shop; purchase");

    }

    private void purchase(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("What do you want to buy?");
        }
        try {
            String item = command.getSecondWord();

            int itemnumber = Integer.parseInt(item);

            Item purchaseItem = dwarvensmithy.getItems().get(itemnumber);
            System.out.println(purchaseItem);
            player.itemadd(purchaseItem);
            dwarvensmithy.itemremove(purchaseItem);
        } catch (Exception e) {

        }
    }

    /**
     * Try to go to one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = null;
        if (direction.equals("north"))
            nextRoom = currentRoom.northExit;
        if (direction.equals("east"))
            nextRoom = currentRoom.eastExit;
        if (direction.equals("south"))
            nextRoom = currentRoom.southExit;
        if (direction.equals("west"))
            nextRoom = currentRoom.westExit;
        if (direction.equals("northwest"))
            nextRoom = currentRoom.northwestExit;

        if (nextRoom == null)
            System.out.println("There is no door!");
        else {
            currentRoom = nextRoom;
            System.out.println("You are " + currentRoom.getDescription());
            System.out.print("Exits: ");
            if (currentRoom.northExit != null)
                System.out.print("north ");
            if (currentRoom.eastExit != null)
                System.out.print("east ");
            if (currentRoom.southExit != null)
                System.out.print("south ");
            if (currentRoom.westExit != null)
                System.out.print("west ");
            System.out.println();
        }
    }

    /**
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game. Return true, if this command
     * quits the game, false otherwise.
     */
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else
            return true; // signal that we want to quit
    }
}
