package database_application;

public class Pujceni {
    private String jmenoZakaznika;
    private String prijmeniZakaznika;
    private String cisloDokladuZakaznika;

    public String getJmenoZakaznika() {
        return jmenoZakaznika;
    }

    public String getPrijmeniZakaznika() {
        return prijmeniZakaznika;
    }

    public String getCisloDokladuZakaznika() {
        return cisloDokladuZakaznika;
    }

    public Pujceni(String jmenoZakaznika, String prijmeniZakaznika, String cisloDokladuZakaznika) {
        this.jmenoZakaznika = jmenoZakaznika;
        this.prijmeniZakaznika = prijmeniZakaznika;
        this.cisloDokladuZakaznika = cisloDokladuZakaznika;
    }
}
