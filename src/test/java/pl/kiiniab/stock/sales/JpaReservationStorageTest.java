package pl.kiiniab.stock.sales;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.kiiniab.stock.sales.offerting.Offer;
import pl.kiiniab.stock.sales.ordering.Reservation;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

@SpringBootTest
public class JpaReservationStorageTest {

    @Autowired
    JpaReservationStorage jpaReservationStorage;

    @Test
    void itAllowsToStoreAndLoadReservation() {
        // given
        Reservation reservation = exampleReservation();

        // when
        jpaReservationStorage.save(reservation);
        Reservation loaded = jpaReservationStorage.load(reservation.getId()).get();

        // then
        assertEquals(reservation.getId(), loaded.getId());
        assertEquals(reservation.getCustomerEmail(), loaded.getCustomerEmail());
        assertTrue(reservation.getTotal().compareTo(loaded.getTotal()) == 0, "loaded to is not equal to initial one");
        //@toDo needsToBeChecked assertEquals(reservation.getLinesCount(), loaded.getLinesCount());

    }

    private Reservation exampleReservation() {
        return Reservation.of(
                Offer.of(BigDecimal.TEN, Collections.emptyList()),
                Arrays.asList(BasketItem.of("image-1", BigDecimal.valueOf(10.00))),
                new CustomerData("it.cello@example.com", "it", "cello")
        );
    }
}