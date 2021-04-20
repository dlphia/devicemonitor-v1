package edu.gatech.cs6440.devicemonitor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "device")
public class DevicePojo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "device_id")
	private Long deviceId;
	
	@Column(name = "fhir_device_id")
	private Long fhirDeviceId;

	@Column(name = "patient_Id")
	private Long patientId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "udi")
	private String udi;
	
	

	public DevicePojo() {
		super();
	}





	public DevicePojo(Long fhirDeviceId, Long patientId, String name, String udi) {
		super();
		this.fhirDeviceId = fhirDeviceId;
		this.patientId = patientId;
		this.name = name;
		this.udi = udi;
	}





	public Long getDeviceId() {
		return deviceId;
	}


	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}


	public Long getFhirDeviceId() {
		return fhirDeviceId;
	}


	public void setFhirDeviceId(Long fhirDeviceId) {
		this.fhirDeviceId = fhirDeviceId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getUdi() {
		return udi;
	}


	public void setUdi(String udi) {
		this.udi = udi;
	}





	public Long getPatientId() {
		return patientId;
	}





	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}




	
	
}