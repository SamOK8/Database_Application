package database_application;

import java.sql.Date;

public class Rent {
    private int id;
    private User customer;
    private Car car;
    private Date since;
    private Date until;

    public Rent() {

    }

    public Rent(User user, Car car) {
        this.customer = user;
        this.car = car;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Car getCar() {
        return car;
    }

    public String getSpz() {
        return car.getSPZ();
    }

    public void setCar(Car car) {
        this.car = car;
    }



    public String getCustomerName() {
        return customer.getName();
    }

    public String getCustomerDrivingLicenseCardNumber() {
        return customer.getDrivingLicenseCardNumber();
    }


    public void setSince(Date since) {
        this.since = since;
    }

    public Date getSince() {
        return since;
    }

    public void setUntil(Date until) {
        this.until = until;
    }

    public Date getUntil() {
        return until;
    }
}
