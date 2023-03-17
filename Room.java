import java.util.*;
/*
 * Class Room - a room in an adventure game.
 *
 * This class is the main class of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
* @author  Marten Wilhelm
 * @version 1.0 (December 2022-2023)
 */

import java.util.ArrayList; // import the ArrayList class
import java.util.List;

class Room {
    public String description;
    public Room northExit;
    public Room southExit;
    public Room eastExit;
    public Room westExit;
    public Room northwestExit;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     */
    public Room(String description) {
        this.description = description;
    }

    /**
     * Define the exits of this room. Every direction either leads
     * to another room or is null (no exit there).
     */
    public void setExits(Room north, Room east, Room south, Room west, Room northwest) {
        if (north != null)
            northExit = north;
        if (east != null)
            eastExit = east;
        if (south != null)
            southExit = south;
        if (west != null)
            westExit = west;
        if (northwest != null)
            northwestExit = northwest;
    }

    /**
     * Return the description of the room (the one that was defined
     * in the constructor).
     */
    public String getDescription() {
        return description;
    }

}

class Shop extends Room {

    private ArrayList<Item> items = new ArrayList<Item>();

    public Shop(String description) {
        super(description);

    }

    public void itemadd(Item shopItem) {
        items.add(shopItem);
    }

    public void itemremove(Item shopItem) {
        items.remove(shopItem);
    }

    public List<Item> getItems() {
        return this.items;
    }

    public void accessShop() {
        Random ran = new Random();
        int randomNumber = ran.nextInt(3);

        if (randomNumber == 0) {
            System.out.println("Wouldst thou be interested in acquiring some goods? Or a weapon perhaps?");
            System.out.println("We have a plethora of weapons for you to choose from:");
        } else if (randomNumber == 1) {
            System.out.println("Ah, a lost soul has seemed to stumble into my humble establishment!");
            System.out.println("Rest and buy some weapons, you know you want to.");
        } else if (randomNumber == 2) {
            System.out.println("Haha, you seem like a fellow who could apreciate a good weapon!");
            System.out.println("You should really buy one!");
        }
        System.out.println();

        for (Item i : this.items) {
            if (i.getprice() == 1) {
                System.out.println("For " + i.getprice() + " Dubloon you can buy a " + i);
            } else {
                System.out.println("For " + i.getprice() + " Dubloons you can buy a " + i);
            }
            System.out.println();
        }

    }
}