package edu.gatech.cs6440.devicemonitor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.gatech.cs6440.devicemonitor.model.UserPojo;


@Repository
public interface UserRepository extends JpaRepository<UserPojo, Long> {
	
	public UserPojo findByUsername(String username);
	public UserPojo findByToken(String token);

}
