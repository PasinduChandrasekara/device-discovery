package com.liquid.devicediscovery.controller;

import com.liquid.devicediscovery.domain.Device;
import com.liquid.devicediscovery.service.DeviceManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    DeviceManagementService deviceManagementService;

    @PostMapping
    public Device addDevice(@RequestBody Device device) {
        try{
            return deviceManagementService.addDevice(device);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/user/{userId}")
    public List<Device> getUserDevices(@RequestParam String userId) {
        List<Device> devices = Collections.emptyList();
        try {
            devices = deviceManagementService.findByUserId(userId);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return devices;
    }

    @GetMapping
    public void getAllDevices(@RequestParam String subnet) throws IOException {
        int timeout=1000;
        for (int i=1;i<255;i++){
            String host=subnet + "." + i;
            if (InetAddress.getByName(host).isReachable(timeout)){
                System.out.println(host + " is reachable");
            }
        }
    }
}
