package com.airportIEproject.database;
//import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airportIEproject.models.User;

public interface UserRepo extends JpaRepository<User, Long> {
	User findById(long id);

	User findByUsername(String user_name);
}
