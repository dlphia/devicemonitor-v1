package edu.gatech.cs6440.devicemonitor.service;

import java.util.List;

import org.hl7.fhir.r4.model.Device;

import edu.gatech.cs6440.devicemonitor.model.DevicePojo;

public interface DeviceService {
	Device findDeviceByUDI(String udi);
	List<DevicePojo> findDevicePojosByUDI(String udi);
	List<Device> findAll();
	Device findByFhirId(Long fhirId);
	
	
}
