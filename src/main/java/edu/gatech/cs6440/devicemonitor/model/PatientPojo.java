package edu.gatech.cs6440.devicemonitor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "patient")
public class PatientPojo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "patient_id")
	@JsonIgnore
	private Long patientId;
	
	@Column(name = "fhir_patient_id")
	private Long fhirPatientId;

	@Column(name = "fname")
	private String firstName;
	
	@Column(name = "lname")
	private String lastName;
	
	@Column(name = "dob")
	private String dob;
	

	public PatientPojo() {
		super();
	}




	public PatientPojo(Long fhirPatientId, String firstName, String lastName, String dob) {
		super();
		this.fhirPatientId = fhirPatientId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
	}




	public Long getPatientId() {
		return patientId;
	}


	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}


	public Long getFhirPatientId() {
		return fhirPatientId;
	}


	public void setFhirPatientId(Long fhirPatientId) {
		this.fhirPatientId = fhirPatientId;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}




	public String getDob() {
		return dob;
	}




	public void setDob(String dob) {
		this.dob = dob;
	}


	
}