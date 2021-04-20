package edu.gatech.cs6440.devicemonitor.controller;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import org.hl7.fhir.r4.model.Device;
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
import edu.gatech.cs6440.devicemonitor.model.PatientPojo;
import edu.gatech.cs6440.devicemonitor.service.DeviceService;
import edu.gatech.cs6440.devicemonitor.service.PatientService;
import edu.gatech.cs6440.devicemonitor.service.UserService;

@RestController
@CrossOrigin(origins="*")
public class DeviceController {

	private Logger logger = getLogger(DeviceController.class);

	@Autowired
	private DeviceService deviceService;
	@Autowired
	private PatientService patientService;
	@Autowired
	private UserService userService;
	/**
	 * get device/fhirId   return Device
	 * get device/fhirId/patients return List<PatientPojo>
	 * get device?udi=aaaaa return Device
	 * 
	 */

	@GetMapping("device/{fhirId}")
	@ResponseBody
	public ResponseEntity<?> findDeviceByFhirId(@PathVariable Long fhirId, @RequestHeader("token") String token) {
		if (userService.auth(token) == null) {
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		}
		Device device = deviceService.findByFhirId(fhirId);
		FhirContext ctx = FhirContext.forR4();
		String response = ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(device);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("device/{udi}/patients")
	@ResponseBody
	public ResponseEntity<?> findPatientsByDeviceFhirId(@PathVariable String udi, @RequestHeader("token") String token) {
		if (userService.auth(token) == null) {
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		}
		List<PatientPojo> patientPojos = patientService.findPatientsByDeviceUDI(udi);
		return new ResponseEntity<>(patientPojos, HttpStatus.OK);
	}

	@GetMapping("device")
	@ResponseBody
	public ResponseEntity<?> findDevice(
			@RequestParam(value = "udi", required = false) String udi,
			@RequestHeader("token") String token) {
		if (userService.auth(token) == null) {
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		}
		
		FhirContext ctx = FhirContext.forR4();
		
		if(udi!=null) {
		Device device = deviceService.findDeviceByUDI(udi);
		if(device==null) {
			return new ResponseEntity<>("Invalid UDI", HttpStatus.BAD_REQUEST);
		}
		
		String deviceJson = ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(device);
		return new ResponseEntity<>(deviceJson, HttpStatus.OK);
		}
		
		List<Device> devices= deviceService.findAll();
		
		JSONArray arr = new JSONArray();

		logger.info("devices length= {}", devices.size());
		for (Device d : devices) {
				String deviceJson = ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(d);
				JSONObject obj = new JSONObject(deviceJson);
				arr.put(obj);
		}
		return new ResponseEntity<>(arr.toString(), HttpStatus.OK);
	}

	@GetMapping("helthcheck")
	public String test() {

		return "Hello DeviceMonitor";
	}


}
