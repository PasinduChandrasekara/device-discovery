package com.liquid.devicediscovery.controller;

import com.google.zxing.WriterException;
import com.liquid.devicediscovery.domain.Device;
import com.liquid.devicediscovery.service.DeviceManagementService;
import com.liquid.devicediscovery.service.QRCodeService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    DeviceManagementService deviceManagementService;

    @Autowired
    QRCodeService qrCodeService;

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

    @GetMapping("/register")
    public byte[] getRegisterationCode() {
        byte[] qrCode = null;
        try {
            qrCode = qrCodeService.getQRCodeImage(new JSONObject(), 100, 250);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return qrCode;
    }
}
