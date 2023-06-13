import java.util.ArrayList;
import java.util.List;

public class Boss {
private ArrayList<Item> inventory = new ArrayList<Item>();
private Room bossRoom;
public Boss(Room place){
    this.bossRoom =place;
}

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

    public Room getLocation(){
        return this.bossRoom;
    }

    public int getHighestATK(){
        int max = Integer.MIN_VALUE;
         ArrayList<Weapon> weapons = new ArrayList<Weapon>();

        for(Item i: inventory){
            if(i instanceof Weapon) {
                weapons.add((Weapon) i);
            }
        }

        for(Weapon w: weapons){
            if(w.getatk()>max) max = w.getatk();
        }

        return max;
    }
}
