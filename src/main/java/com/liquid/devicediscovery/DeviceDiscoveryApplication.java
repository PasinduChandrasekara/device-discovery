package com.liquid.devicediscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DeviceDiscoveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeviceDiscoveryApplication.class, args);
    }

}
