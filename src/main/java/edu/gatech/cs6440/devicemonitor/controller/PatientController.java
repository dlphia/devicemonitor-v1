package edu.gatech.cs6440.devicemonitor.controller;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import org.hl7.fhir.r4.model.Patient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ca.uhn.fhir.context.FhirContext;
import edu.gatech.cs6440.devicemonitor.service.PatientService;
import edu.gatech.cs6440.devicemonitor.service.UserService;

@RestController
@CrossOrigin(origins="*")
public class PatientController {
	
	private Logger logger = getLogger(PatientController.class);
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private UserService userService;
	/**
	 * get patient  parameters:fhirId,fname,lname,dob returns List<Patient>
	 * get patient/fhirPatientId returns Patient
	 *
	 */
	
	@GetMapping("patient/{fhirId}")
	@ResponseBody
    public ResponseEntity<?> findPatientByFhirId(@PathVariable Long fhirId, @RequestHeader("token") String token) {
		logger.info("findPatientByFhirId fhirId= {}", fhirId);
		if(userService.auth(token)==null) {
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		}
		Patient patient =patientService.findByFhirPatientId(fhirId);
		FhirContext ctx = FhirContext.forR4();
		String response = ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(patient);
		return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	@GetMapping("patient")
	@ResponseBody
    public ResponseEntity<?> findPatients(
    		@RequestParam(required = false) Long fhirId, 
    		@RequestParam(required = false) String firstName,
    		@RequestParam(required = false) String lastName,
    		@RequestParam(required = false) String dob,
    		@RequestHeader("token") String token) {
		if(userService.auth(token)==null) {
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		}
		JSONArray arr = new JSONArray();
		FhirContext ctx = FhirContext.forR4();
		List<Patient> patients =patientService.findPatients(fhirId, firstName, lastName, dob);
		for (Patient p : patients) {
			String patientJson = ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(p);
			JSONObject obj = new JSONObject(patientJson);
			arr.put(obj);
		}
		return new ResponseEntity<>(arr.toString(), HttpStatus.OK);
    }
	

}
