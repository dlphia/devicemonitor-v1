package edu.gatech.cs6440.devicemonitor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.gatech.cs6440.devicemonitor.model.DevicePojo;


@Repository
public interface DeviceRepository extends JpaRepository<DevicePojo, Long> {
	
	public List<DevicePojo> findByUdi(String udi);
	public List<DevicePojo> findByPatientId(Long patientId);
}
