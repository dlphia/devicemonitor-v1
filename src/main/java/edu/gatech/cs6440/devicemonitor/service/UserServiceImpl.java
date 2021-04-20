package edu.gatech.cs6440.devicemonitor.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.gatech.cs6440.devicemonitor.model.UserPojo;
import edu.gatech.cs6440.devicemonitor.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;

	/**
	 * returns authentication token if the credentials are valid, otherwise returns null
	 */
	@Override
	public String login(String username, String password) {
		UserPojo user= userRepository.findByUsername(username);
		if(user!=null && user.getPassword().equals(password)) {
			//use UUID as token generator for simple authentication purpose
			String token = UUID.randomUUID().toString();
			user.setToken(token);
			userRepository.save(user);
			return token;
		}
		return null;
	}

	@Override
	public UserPojo auth(String token) {
		UserPojo user= userRepository.findByToken(token);
		if(user!=null) {
			return user;
		}
		return null;
	}


	
}
