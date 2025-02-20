package database_application;

public class Vozidlo {
    private String znacka;
    private String model;
    private String SPZ;

    public Vozidlo(String spz, String znacka, String model) {
        this.SPZ = spz;
        this.znacka = znacka;
        this.model = model;
    }

    public String getZnacka() {return znacka;}
    public String getModel() {return model;}
    public String getSPZ() {return SPZ;}

}
