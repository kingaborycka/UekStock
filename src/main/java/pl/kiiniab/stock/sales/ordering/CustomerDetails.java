package pl.kiiniab.stock.sales.ordering;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class CustomerDetails {
    String firstname;
    String lastname;
    String email;

    public CustomerDetails(String firstname, String lastname, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;

    }

    public CustomerDetails() {

    }

    public static CustomerDetails of(String firstname, String lastname, String email) {
        return new CustomerDetails(firstname, lastname, email);
    }
}