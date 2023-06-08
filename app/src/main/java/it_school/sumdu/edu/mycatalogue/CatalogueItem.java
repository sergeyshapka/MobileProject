package it_school.sumdu.edu.mycatalogue;

public class CatalogueItem {
    private Integer Number;
    private String Name;
    private String Amount;
    private String Price;
    private String ItemDescription;
    private byte[] Image;
    public CatalogueItem(int number, String name, String amount, String price, String itemDescription, byte[] image) {
        Number = number;
        Name = name;
        Amount = amount;
        Price = price;
        ItemDescription = itemDescription;
        Image = image;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAmount() {return Amount;    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {Price = price;    }

    public String getItemDescription() {
        return ItemDescription;
    }

    public void setItemDescription(String itemDescription) {
        ItemDescription = itemDescription;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        Image = image;
    }
}

