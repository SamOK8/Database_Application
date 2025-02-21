package database_application;

public class Car {
    private int id;
    private String brand;
    private String model;
    private String SPZ;

    public Car(String spz, String brand, String model) {
        this.SPZ = spz;
        this.brand = brand;
        this.model = model;
    }

    public Car(String spz) {
        this.SPZ = spz;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getBrand() {return brand;}
    public String getModel() {return model;}
    public String getSPZ() {return SPZ;}

}
