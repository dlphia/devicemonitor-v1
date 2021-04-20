package edu.gatech.cs6440.devicemonitor.util;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.gatech.cs6440.devicemonitor.model.DevicePojo;
import edu.gatech.cs6440.devicemonitor.model.PatientPojo;
import edu.gatech.cs6440.devicemonitor.model.UserPojo;
import edu.gatech.cs6440.devicemonitor.repository.DeviceRepository;
import edu.gatech.cs6440.devicemonitor.repository.PatientRepository;
import edu.gatech.cs6440.devicemonitor.repository.UserRepository;

@Component
public class DataInitializer {
	
	private Logger logger = getLogger(DataInitializer.class);
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private DeviceRepository deviceRepository;
	
	@Autowired
    private PatientRepository patientRepository;
	
	 @PostConstruct
	 private void initializeTestUser() {
		 UserPojo testUser = userRepository.findByUsername("test");
		 if(testUser==null) {
			 testUser = new UserPojo();
			 testUser.setEmail("test@test.com");
			 testUser.setUsername("test");
			 testUser.setPassword("test");
			 testUser.setToken("test");
			 userRepository.save(testUser);
		 }
		 
		 List<DevicePojo> devices= deviceRepository.findAll();
		 
		 logger.info("devices.size= {}",devices.size());
		 
		 List<PatientPojo> patients= patientRepository.findAll();
		 
		 logger.info("patients.size= {}",patients.size());
		 
		 
	 }
	

}
