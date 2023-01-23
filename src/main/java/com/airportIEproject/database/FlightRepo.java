package com.airportIEproject.database;

import org.springframework.data.jpa.repository.JpaRepository;
import com.airportIEproject.models.Flight;

public interface FlightRepo extends JpaRepository<Flight, Long> {
	Flight findById(long id);
}
