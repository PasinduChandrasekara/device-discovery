package com.liquid.devicediscovery.dto;

import java.util.Map;

public class RegistrationInfoDto {

  private byte[] qrCode;

  private String bearerToken;

  private Map<String, String> metaData;

  public byte[] getQrCode() {
    return qrCode;
  }

  public void setQrCode(byte[] qrCode) {
    this.qrCode = qrCode;
  }

  public String getBearerToken() {
    return bearerToken;
  }

  public void setBearerToken(String bearerToken) {
    this.bearerToken = bearerToken;
  }

  public Map<String, String> getMetaData() {
    return metaData;
  }

  public void setMetaData(Map<String, String> metaData) {
    this.metaData = metaData;
  }
}
