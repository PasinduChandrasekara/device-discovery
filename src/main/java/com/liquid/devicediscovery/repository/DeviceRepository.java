package com.liquid.devicediscovery.repository;

import com.liquid.devicediscovery.domain.Device;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DeviceRepository extends CrudRepository<Device, String> {

    List<Device> findByUserId(String Id);
}
