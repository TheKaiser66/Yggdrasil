class Item {
private int price;
private int weight;
private int atk;
private String description;

public Item(int price,int atk, int weight,String description)
{
    this.price = price;
    this.atk = atk;
    this.weight = weight;
    this.description = description;
}

public int getprice()
{
    return this.price;
}

public int getatk()
{
    return this.atk;
}

public int getweight()
{
    return this.weight;
}

public String getdescription()
{
    return this.description;
}

@Override
public String toString(){
    return this.description;
}
}
