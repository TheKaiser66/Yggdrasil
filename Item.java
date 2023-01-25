class Item {
private int price;
private int weight;
private String description;

public Item(int price, int weight,String description)
{
    this.price = price;
    this.weight = weight;
    this.description = description;
}

public int getprice()
{
    return this.price;
}

public int getweight()
{
    return this.weight;
}

public String getdescription()
{
    return this.description;
}
}
