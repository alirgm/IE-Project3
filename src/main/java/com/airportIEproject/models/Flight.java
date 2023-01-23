package com.airportIEproject.models;

import jakarta.persistence.*;
import lombok.NonNull;

import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "flights")
public class Flight {
	@Id
	@GeneratedValue
	private Long id;
	@NonNull
	private String flight_from;
	private String flight_to;
	private Date date;
	private Time takeoff_time;
	private Time landing_time;
	private int capacity;
	private int reserved;
	private Long price;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Time getLanding_time() {
		return landing_time;
	}

	public void setLanding_time(Time landing_time) {
		this.landing_time = landing_time;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getTakeoff_time() {
		return takeoff_time;
	}

	public void setTakeoff_time(Time takeoff_time) {
		this.takeoff_time = takeoff_time;
	}

	public String getFlight_from() {
		return flight_from;
	}

	public void setFlight_from(String flight_from) {
		this.flight_from = flight_from;
	}

	public String getFlight_to() {
		return flight_to;
	}

	public void setFlight_to(String flight_to) {
		this.flight_to = flight_to;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getReserved() {
		return reserved;
	}

	public void setReserved(int reserved) {
		this.reserved = reserved;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}
}
