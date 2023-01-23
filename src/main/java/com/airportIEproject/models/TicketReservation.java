package com.airportIEproject.models;

import java.sql.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "tickets")
public class TicketReservation {
	@Id
	@GeneratedValue
	private Long id;

	private String first_name;
	private String last_name;
	private String father_name;
	private String nationalCode;
	private String gender;
	private Date b_date;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "flight_id", referencedColumnName = "id")
	private Flight flightId;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User userId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getFather_name() {
		return father_name;
	}

	public void setFather_name(String father_name) {
		this.father_name = father_name;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public Date getB_date() {
		return b_date;
	}

	public void setB_date(Date b_date) {
		this.b_date = b_date;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Flight getFlightId() {
		return flightId;
	}

	public void setFlightId(Flight flightId) {
		this.flightId = flightId;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public String getNationalCode() {
		return nationalCode;
	}

	public void setNationalCode(String nationalCode) {
		this.nationalCode = nationalCode;
	}

//    @ManyToOne(cascade=CascadeType.PERSIST)
//    private Airplane airplane_id;
}
