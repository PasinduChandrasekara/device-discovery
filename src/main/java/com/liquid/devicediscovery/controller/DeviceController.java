package com.liquid.devicediscovery.controller;

import com.liquid.devicediscovery.domain.Device;
import com.liquid.devicediscovery.dto.DeviceDto;
import com.liquid.devicediscovery.dto.RegistrationInfoDto;
import com.liquid.devicediscovery.service.DeviceManagementService;
import com.liquid.devicediscovery.service.QRCodeService;
import org.json.JSONObject;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    DeviceManagementService deviceManagementService;

    @Autowired
    QRCodeService qrCodeService;

    @Autowired
    ModelMapper modelMapper;

    private final String REGISTRATION_URL = "serveo.net:8091/devices/";
    private final String TOKEN_URL = "serveo.net:8091/devices/register/token";

    private static final Type DEVICE_LIST_TYPE = new TypeToken<List<DeviceDto>>() {
    }.getType();

    @PostMapping
    public Device addDevice(@RequestBody DeviceDto deviceDto) {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @GetMapping("/register/token")
    public String getBearerToken() {
        KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        KeycloakPrincipal keycloakPrincipal = (KeycloakPrincipal) authentication.getPrincipal();
        return keycloakPrincipal.getKeycloakSecurityContext().getTokenString();
    }

    @GetMapping("/register")
    public RegistrationInfoDto getRegistrationCode(@RequestParam String redirectUrl, @RequestParam
            Map<String, String> metaData) {
        RegistrationInfoDto registrationInfoDto = null;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("redirectUrl", redirectUrl);
        jsonObject.put("registrationUrl", REGISTRATION_URL);
        jsonObject.put("tokenUrl", TOKEN_URL);
        jsonObject.put("metaData", metaData);
        try {
            byte[] qrCode = qrCodeService.getQRCodeImage(jsonObject, 300, 300);
            registrationInfoDto = new RegistrationInfoDto();
            registrationInfoDto.setQrCode(qrCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return registrationInfoDto;
    }
}
