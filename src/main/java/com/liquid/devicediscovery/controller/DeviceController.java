package com.liquid.devicediscovery.controller;

import com.google.zxing.WriterException;
import com.liquid.devicediscovery.domain.Device;
import com.liquid.devicediscovery.dto.DeviceDto;
import com.liquid.devicediscovery.dto.RegistrationInfoDto;
import com.liquid.devicediscovery.service.DeviceManagementService;
import com.liquid.devicediscovery.service.QRCodeService;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    DeviceManagementService deviceManagementService;

    @Autowired
    QRCodeService qrCodeService;

    @Autowired
    ModelMapper modelMapper;

    private final String REGISTRATION_URL = "http://localhost:8090/discovery/";

    private static final Type DEVICE_LIST_TYPE = new TypeToken<List<DeviceDto>>(){}.getType();

    @PostMapping
    public Device addDevice(@RequestBody DeviceDto deviceDto) {
        try{
            return deviceManagementService.addDevice(modelMapper.map(deviceDto, Device.class));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/user/{userId}")
    public List<DeviceDto> getUserDevices(@RequestParam String userId) {
        try {
            List<Device> devices = deviceManagementService.findByUserId(userId);
            if (!devices.isEmpty()) {
                return modelMapper.map(devices, DEVICE_LIST_TYPE);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @GetMapping("/register")
    public RegistrationInfoDto getRegisterationCode(@RequestParam String redirectUrl, @RequestParam
        Map<String, String> metaData) {
        RegistrationInfoDto registrationInfoDto = null;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("redirectUrl", redirectUrl);
        jsonObject.put("registrationUrl", REGISTRATION_URL);
        try {
            byte[] qrCode = qrCodeService.getQRCodeImage(jsonObject, 100, 250);
            registrationInfoDto = new RegistrationInfoDto();
            registrationInfoDto.setQrCode(qrCode);
            registrationInfoDto.setMetaData(metaData);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return registrationInfoDto;
    }
}
