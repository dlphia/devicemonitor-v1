package edu.gatech.cs6440.devicemonitor.service;

import edu.gatech.cs6440.devicemonitor.model.UserPojo;

public interface UserService {

	String login(String username, String password);
	UserPojo auth(String token);
	
	
	
}
