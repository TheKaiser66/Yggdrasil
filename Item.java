
class Item {
    private int price;
    private int weight;
    private String description;

    public Item(int price, int weight, String description) {
        this.price = price;
        this.weight = weight;
        this.description = description;
    }

    public int getprice() {
        return this.price;
    }

    public int getweight() {
        return this.weight;
    }

    public String getdescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}

class Weapon extends Item {
    private int atk;

    public Weapon(int price, int atk, int weight, String description) {
        super(price, weight, description);
        this.atk = atk;
    }

    public int getatk() {
        return this.atk;
    }
}

class Food extends Item {
    private int healing;

    public Food(int price, int healing, int weight, String description) {
        super(price, weight, description);
        this.healing = healing;
    }

    public int gethealing() {
        return this.healing;
    }

}

class Armour extends Item {
    private int def;

    public Armour(int price, int def, int weight, String description){
        super(price, weight, description);
        this.def = def;
    }

    public int getdef(){
        return this.def;
    }
}