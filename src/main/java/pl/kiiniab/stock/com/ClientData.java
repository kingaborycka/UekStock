package pl.kiiniab.stock.com;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ClientData {

    @Id
    @GeneratedValue
    private Integer id;

    private String firstName;
    private String lastname;

    @Embedded
    private Address address;

}
