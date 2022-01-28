package id.ac.binus.vegetarianshop;

public class Vege {
    private String name;
    private int price;
    private String description;
    private int imageId;

    public Vege(String name, int price, String description, int imageId) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
