package com.liquid.devicediscovery.service;

import com.liquid.devicediscovery.domain.Device;
import com.liquid.devicediscovery.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceManagementService {

    @Autowired
    DeviceRepository deviceRepository;

    public Device addDevice(Device device) {
        return deviceRepository.save(device);
    }

    public List<Device> findByUserId(String userId) {
        return deviceRepository.findByUserId(userId);
    }

}
