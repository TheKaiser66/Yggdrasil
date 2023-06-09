import java.util.ArrayList;
import java.util.List;

public class Boss {
private ArrayList<Item> inventory = new ArrayList<Item>();

    public void itemadd(Item invItem) {
        inventory.add(invItem);
    }

    public void itemremove(Item invItem) {
        inventory.remove(invItem);
    }
    
    public List<Item> getItems() {
        return this.inventory;
    }

    public void accessInventory() {
        System.out.println(this.inventory);
    }

    public int HP = 100;

    public void bossHP(){
        System.out.println("The boss has " + HP + " HP left!");
    }
}
