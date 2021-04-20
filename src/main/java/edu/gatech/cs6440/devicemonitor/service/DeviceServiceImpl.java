package edu.gatech.cs6440.devicemonitor.service;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.r4.model.Device;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import edu.gatech.cs6440.devicemonitor.model.DevicePojo;
import edu.gatech.cs6440.devicemonitor.repository.DeviceRepository;

@Service
public class DeviceServiceImpl implements DeviceService{
	
	private Logger logger = getLogger(DeviceServiceImpl.class);
	
	@Autowired
	private DeviceRepository deviceRepository;
	
	public static final String  CLIENT_URL ="https://hapi.fhir.org/baseR4";

	
	@Override
	public Device findDeviceByUDI(String udi) {
		List<DevicePojo> devicePojos = deviceRepository.findByUdi(udi);
		if(devicePojos==null || devicePojos.size()==0) {
			return null;
		}
		FhirContext ctx = FhirContext.forR4();
	    IGenericClient client = ctx.newRestfulGenericClient(CLIENT_URL);
    	Device device = client.read().resource(Device.class).withId(devicePojos.get(0).getFhirDeviceId()).execute();
		return device;
	}

	@Override
	public List<Device> findAll() {
		List<DevicePojo> devicePojos = deviceRepository.findAll();
		List<Device> devices = new ArrayList<>();
		if(devicePojos==null || devicePojos.size()==0){
			return devices;
		}
		FhirContext ctx = FhirContext.forR4();
	    IGenericClient client = ctx.newRestfulGenericClient(CLIENT_URL);
	    for(DevicePojo d : devicePojos) {
	    	Device device = client.read().resource(Device.class).withId(d.getFhirDeviceId()).execute();
	    	devices.add(device);
	    }
	    logger.info("findAll devices.size={ }",devices.size());
		return devices;
	}

	@Override
	public Device findByFhirId(Long fhirId) {
		FhirContext ctx = FhirContext.forR4();
	    IGenericClient client = ctx.newRestfulGenericClient(CLIENT_URL);
	    Device device = client.read().resource(Device.class).withId(fhirId).execute();
		return device;
	}

	@Override
	public List<DevicePojo> findDevicePojosByUDI(String udi) {
		List<DevicePojo> devices = deviceRepository.findByUdi(udi);
		logger.info("findDevicePojosByUDI devices.size={ }",devices.size());
		return devices;
	}
	
	
	
}
