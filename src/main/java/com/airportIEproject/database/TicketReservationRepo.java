package com.airportIEproject.database;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Collection;

import com.airportIEproject.models.Flight;
import com.airportIEproject.models.User;
import com.airportIEproject.models.TicketReservation;

public interface TicketReservationRepo extends JpaRepository<TicketReservation, Long> {
	TicketReservation findByNationalCode(String nationalCode);

	TicketReservation findById(long id);

	Collection<TicketReservation> findByFlightId(Flight flight);
	Collection<TicketReservation> findByUserId(User user);
	TicketReservation findByFlightIdAndNationalCode(Flight flight, String nationalCode);
}
