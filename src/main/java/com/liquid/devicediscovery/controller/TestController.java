package com.liquid.devicediscovery.controller;

import org.springframework.mobile.device.Device;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestController {

    @GetMapping
    public String getTest(Device device) {
        if (device.isMobile()) {
            return "Hello mobile user!";
        } else if (device.isTablet()) {
            return "Hello tablet user!";
        } else {
            return "Hello desktop user!";
        }
    }
}
