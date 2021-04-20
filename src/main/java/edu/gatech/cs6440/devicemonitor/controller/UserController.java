package edu.gatech.cs6440.devicemonitor.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.gatech.cs6440.devicemonitor.model.UserLoginDTO;
import edu.gatech.cs6440.devicemonitor.service.UserService;

@RestController
@CrossOrigin(origins="*")
public class UserController {
	
	
	@Autowired
	private UserService userService;
	
	@PostMapping("user/login")
	@ResponseBody
    public ResponseEntity<?> findPatientById(@RequestBody UserLoginDTO body) {
		
		String token = userService.login(body.getUsername(), body.getPassword());
		if(token==null) {
			return new ResponseEntity<String>("Invalid credentials", HttpStatus.BAD_REQUEST);
		}
		Map<String,String> response = new HashMap<>();
		response.put("token", token);
		return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	@GetMapping("user/token")
	@ResponseBody
    public ResponseEntity<?> tokenValidation(@RequestHeader("token") String token) {
		if (userService.auth(token) == null) {
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<>("", HttpStatus.OK);
    }

}
