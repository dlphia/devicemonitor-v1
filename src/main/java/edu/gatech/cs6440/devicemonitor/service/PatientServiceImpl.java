package edu.gatech.cs6440.devicemonitor.service;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.r4.model.Patient;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import edu.gatech.cs6440.devicemonitor.model.DevicePojo;
import edu.gatech.cs6440.devicemonitor.model.PatientPojo;
import edu.gatech.cs6440.devicemonitor.repository.PatientRepository;

@Service
public class PatientServiceImpl implements PatientService {
	
	private Logger logger = getLogger(PatientServiceImpl.class);

	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private DeviceService deviceService;

	public static final String CLIENT_URL = "https://hapi.fhir.org/baseR4";

	@Override
	public List<PatientPojo> findByNames(String firstName, String lastName) {
		return patientRepository.findByNames(firstName, lastName);
	}

	@Override
	public Patient findByFhirPatientId(Long id) {
		FhirContext ctx = FhirContext.forR4();
		IGenericClient client = ctx.newRestfulGenericClient(CLIENT_URL);
		Patient patient = client.read().resource(Patient.class).withId(id).execute();
		return patient;
	}

	@Override
	public List<Patient> findPatients(Long fhirId, String fname, String lname, String dob) {
		List<Patient> ret = new ArrayList<> ();
		FhirContext ctx = FhirContext.forR4();
		IGenericClient client = ctx.newRestfulGenericClient(CLIENT_URL);
		List<PatientPojo> patients = patientRepository.findByCustomizedCondition(fhirId,fname,lname,dob);
		Patient patient = null;
		for(PatientPojo p : patients) {
			patient =client.read().resource(Patient.class).withId(p.getFhirPatientId()).execute();
			ret.add(patient);
		}
		return ret;
	}

	@Override
	public List<PatientPojo> findPatientsByDeviceUDI(String udi) {
		List<DevicePojo> devices = deviceService.findDevicePojosByUDI(udi);
		List<PatientPojo> patients = new ArrayList<>();
		for(DevicePojo d : devices) {
			patients.add(patientRepository.findById(d.getPatientId()).orElse(null));
		}
		logger.info("findPatientsByDeviceUDI patients.size= {}", patients.size());
		return patients;
	}


}
