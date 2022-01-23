package pl.kiiniab.stock.sales.ordering;

import javax.persistence.*;

@Entity
public class ReservationLine {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    Reservation reservation;

    private String imageId;
    private int quantity;

    public ReservationLine() {};

    public ReservationLine(String imageId, int quantity) {
        this.imageId = imageId;
        this.quantity = quantity;
    }
}