/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Marten Wilhelm
 * @version 1.0 (December 2022-2023)
 */

class Game 
{
    // ~~~ initialization ~~~
    public static void main(String args[]) {
        Game g = new Game();
        g.play();
    }

    private Parser parser;
    public static Room currentRoom;

    public static Room roadside, freyasquare, infiniteforest, jormungandr;
    public Shop dwarvensmithy;
    private Weapon wooddenSword, commonersSword;
    private Item smallhealingPotion;

    private int Dubloons = 10;

    //text farben//
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";

    //5 Slots keine 6//
    private String[] inventory = new String[5];


    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        createItems();
        parser = new Parser();
    }

private void createItems()
{
    wooddenSword = new Weapon(1,2,2,"useless woodden Sword");
    commonersSword = new Weapon(10,5,2,"Commoners Sword");
    smallhealingPotion = new Item(5,1,"Small healing Potion");
    dwarvensmithy.itemadd(commonersSword);
    dwarvensmithy.itemadd(wooddenSword);
    dwarvensmithy.itemadd(smallhealingPotion);
}
    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
      
      
        // create the rooms
        roadside = new Room("standing next to a signpost");
        freyasquare = new Room("entering the freyasquare");
        dwarvensmithy = new Shop("in the dwarvensmithy");
        infiniteforest = new Room("in the infinite forest, you are trapped!");
        jormungandr = new Room("fucked, the midgardsnake will eat you!?!");
        
        // initialise room exits
        roadside.setExits(jormungandr, freyasquare,infiniteforest,null);
        freyasquare.setExits(dwarvensmithy, null, null, roadside);
        dwarvensmithy.setExits(null, null, freyasquare, null);
        infiniteforest.setExits(infiniteforest, infiniteforest, infiniteforest,infiniteforest );
        jormungandr.setExits(null, null, roadside, null);
        currentRoom = roadside;  // start game roadside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        if(currentRoom == infiniteforest){
            System.out.println("Congratulations you escaped the infinite forest by killing yourself, start again!");
        }
        else {
        System.out.println("Thank you for playing.  Good bye.");}
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
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
        if(currentRoom.northExit != null)
            System.out.print("north ");
        if(currentRoom.eastExit != null)
            System.out.print("east ");
        if(currentRoom.southExit != null)
            System.out.print("south ");
        if(currentRoom.westExit != null)
            System.out.print("west ");
        System.out.println();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help"))
            printHelp();
        else if (commandWord.equals("go"))
            goRoom(command);
        else if (commandWord.equals("quit"))
            wantToQuit = quit(command);
        else if (commandWord.equals("inventory"))
            wantToAccesInventory();
        else if (commandWord.equals("dubloons"))
            System.out.println("You have " + Dubloons + " dubloons");
        else if (commandWord.equals("shop"))
            if (currentRoom == dwarvensmithy )
            {
                accesShop();
            }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around Yggdrasil.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println("   go quit help inventory");

    }

    /** 
     * Try to go to one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = null;
        if(direction.equals("north"))
            nextRoom = currentRoom.northExit;
        if(direction.equals("east"))
            nextRoom = currentRoom.eastExit;
        if(direction.equals("south"))
            nextRoom = currentRoom.southExit;
        if(direction.equals("west"))
            nextRoom = currentRoom.westExit;

        if (nextRoom == null)
            System.out.println("There is no door!");
        else {
            currentRoom = nextRoom;
            System.out.println("You are " + currentRoom.getDescription());
            System.out.print("Exits: ");
            if(currentRoom.northExit != null)
                System.out.print("north ");
            if(currentRoom.eastExit != null)
                System.out.print("east ");
            if(currentRoom.southExit != null)
                System.out.print("south ");
            if(currentRoom.westExit != null)
                System.out.print("west ");
            System.out.println();
        }
    }

    private void wantToAccesInventory()
    {
        for(int i = 0; i< inventory.length; i++)
        {
            System.out.println(inventory[i]);
        }
    }


    private void accesShop()
    {
        System.out.println("Wouldst thou be interested in acquiring some goods? Or a weapon perhaps?");
        System.out.println("");
        System.out.println("We have a plethora of weapons for you to choose from:");
        System.out.println("For 1 Dubloon you can buy a " + wooddenSword);
        System.out.println("If you want something that packs a punch you should the "+ commonersSword + " for 10 Dubloons");
        System.out.println();
        System.out.println("We also have a few goodies you might be interested in:");
        System.out.println("If you're low on life you should probably buy a " + smallhealingPotion + " for 5 Dubloons");
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game. Return true, if this command
     * quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else
            return true;  // signal that we want to quit
    }
}
