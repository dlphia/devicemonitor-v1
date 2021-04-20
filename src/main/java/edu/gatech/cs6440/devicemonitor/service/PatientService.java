package edu.gatech.cs6440.devicemonitor.service;


import java.util.List;

import org.hl7.fhir.r4.model.Patient;

import edu.gatech.cs6440.devicemonitor.model.PatientPojo;

public interface PatientService {

	List<PatientPojo> findByNames(String firstName, String lastName);
	List<Patient> findPatients(Long fhirId,String fname,String lname,String dob);
	Patient findByFhirPatientId(Long id);
	List<PatientPojo> findPatientsByDeviceUDI(String udi);
	
}
