package pl.kiiniab.stock.com;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    private String street;
    private String zpi;
    private String country;

    public Address(String street, String zpi, String country) {
        this.street = street;
        this.zpi = zpi;
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZpi() {
        return zpi;
    }

    public void setZpi(String zpi) {
        this.zpi = zpi;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
