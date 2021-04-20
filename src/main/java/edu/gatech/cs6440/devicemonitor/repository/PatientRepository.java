package edu.gatech.cs6440.devicemonitor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.gatech.cs6440.devicemonitor.model.PatientPojo;

@Repository
public interface PatientRepository extends JpaRepository<PatientPojo, Long> {

	public final static String FIND_BY_NAMES_QUERY = "SELECT p "
			+ "FROM PatientPojo p WHERE p.firstName = :firstName AND p.lastName = :lastName";

	@Query(FIND_BY_NAMES_QUERY)
	public List<PatientPojo> findByNames(String firstName, String lastName);

	@Query("SELECT p FROM PatientPojo p WHERE (:fhirId is null or p.fhirPatientId = :fhirId) "
			+ "and (:fname is null or p.firstName = :fname) "
			+ "and (:lname is null or p.lastName = :lname) "
			+ "and (:dob is null or p.dob = :dob)")
	public List<PatientPojo> findByCustomizedCondition(Long fhirId, String fname, String lname, String dob);


}
