package com.airportIEproject.controllers;

import org.springframework.http.HttpStatus;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.airportIEproject.database.FlightRepo;
import com.airportIEproject.database.UserRepo;
import com.airportIEproject.database.TicketReservationRepo;
import com.airportIEproject.models.Flight;
import com.airportIEproject.models.User;
import com.airportIEproject.models.TicketReservation;
import com.airportIEproject.models.TicketIn;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
//import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api")

public class Controllers {
	private final Logger log = LoggerFactory.getLogger(Controllers.class);

//	@Autowired
	private UserRepo user_repo;
	private FlightRepo flight_repo;
	private TicketReservationRepo ticket_repo;

	public Controllers(UserRepo user_repo, FlightRepo flight_repo, TicketReservationRepo ticket_repo) {
		this.user_repo = user_repo;
		this.flight_repo = flight_repo;
		this.ticket_repo = ticket_repo;
	}

	// function for hashing user passwords
	public static String hash_pass(String password) {
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byte[] bytes = md.digest();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return generatedPassword;
	}

//	USER ENDPOINTS --------------------------------------------------

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user) throws URISyntaxException {
		System.out.println(user);
		log.info("Request for login: {}", user);
//		User result = user_repo.save(user);
		Optional<User> tmp_user = Optional.ofNullable(user_repo.findByUsername(user.getUsername()));
		if (tmp_user.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			String current = tmp_user.get().getHashed_password();
			String entered = hash_pass(user.getHashed_password());
			if (current.equals(entered)) {
				tmp_user = Optional.ofNullable(user_repo.findByUsername(user.getUsername()));
				return tmp_user.map(response -> ResponseEntity.ok().body(response))
						.orElse(new ResponseEntity<>(HttpStatus.OK));
			} else {
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
		}
	}

	@GetMapping("/user/{username}")
	public ResponseEntity<?> getPassenger(@PathVariable String username) {
		Optional<User> user = Optional.ofNullable(user_repo.findByUsername(username));
		return user.map(response -> ResponseEntity.ok().body(response))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping("/user/register")
	public ResponseEntity<User> createPassenger(@RequestBody User user) throws URISyntaxException {
		log.info("Request to create user: {}", user);
		Optional<User> check = Optional.ofNullable(user_repo.findByUsername(user.getUsername()));

		user.setHashed_password(hash_pass(user.getHashed_password()));

		if (check.isEmpty()) {
			User result = user_repo.save(user);
			return ResponseEntity.created(new URI("/api/user/" + result.getId())).body(result);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

//	FLIGHT ENDPOINTS --------------------------------------------------

	@GetMapping("/flights")
	public Collection<Flight> getAllFlights() {
		return flight_repo.findAll();
	}

	@PostMapping("/flight/create")
	public ResponseEntity<Flight> newFlight(@RequestBody Flight flight) throws URISyntaxException {
		log.info("Request to create new flight: {}", flight);
		Flight result = flight_repo.save(flight);
		return ResponseEntity.created(new URI("/api/user/" + result.getId())).body(result);
	}

//	TICKET ENDPOINTS --------------------------------------------------

	@GetMapping("/tickets")
	public Collection<TicketReservation> getAllTickets() {
		return ticket_repo.findAll();
	}

	@GetMapping("/tickets/{flight_id}")
	public Collection<TicketReservation> getTicketsOfFlight(@PathVariable int flight_id) {
		Flight flight = flight_repo.findById(flight_id);
		Collection<TicketReservation> tickets = ticket_repo.findByFlightId(flight);
		return tickets;
	}

	@GetMapping("/mytickets/{user_id}")
	public Collection<TicketReservation> getTicketsOfUser(@PathVariable int user_id) {
		User user = user_repo.findById(user_id);
		Collection<TicketReservation> tickets = ticket_repo.findByUserId(user);
		return tickets;
	}

	@PostMapping("/ticket/reserve/{user_id}/{flight_id}")
	public ResponseEntity<TicketReservation> reserveTicket(@RequestBody TicketIn ticketIn, @PathVariable int user_id,
			@PathVariable int flight_id) throws URISyntaxException {
//		log.info("Request to reserve new ticket: {}", ticket);
		Flight flight = flight_repo.findById(flight_id);
		User user = user_repo.findById(user_id);

		Optional<TicketReservation> check = Optional
				.ofNullable(ticket_repo.findByFlightIdAndNationalCode(flight, ticketIn.getNationalCode()));
		if (check.isEmpty()) {
			flight.setReserved(flight.getReserved() + 1);
			TicketReservation result = new TicketReservation();
			result.setUserId(user);
			result.setFlightId(flight);
			result.setB_date(ticketIn.getB_date());
			result.setFirst_name(ticketIn.getFirst_name());
			result.setLast_name(ticketIn.getLast_name());
			result.setFather_name(ticketIn.getFather_name());
			result.setNationalCode(ticketIn.getNationalCode());
			result.setGender(ticketIn.getGender());
			TicketReservation x = ticket_repo.save(result);
			return ResponseEntity.created(new URI("/api/user/" + x.getId())).body(result);
		} else {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
}
