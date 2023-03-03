import java.util.ArrayList;

public class Player {
    private ArrayList<Item> inventory = new ArrayList<Item>();

    public void itemadd(Item invItem) {
        inventory.add(invItem);
    }

    public void itemremove(Item invItem) {
        inventory.remove(invItem);
    }

}
