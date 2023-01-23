package com.airportIEproject.models;

import jakarta.persistence.*;
import lombok.NonNull;
//import java.sql.Date;

@Entity
@Table(name = "users")

public class User {
	@Id
	@GeneratedValue
	private Long id;
	@NonNull
	private String username;
	private String hashed_password;
	private String role;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getHashed_password() {
		return hashed_password;
	}

	public void setHashed_password(String hashed_password) {
		this.hashed_password = hashed_password;
	}

}
