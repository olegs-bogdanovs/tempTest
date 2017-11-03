package com.controlpanel;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class TemperatureMetric {
    private String deviceMAC;
    private float temp;
    private Date date;

    public TemperatureMetric(){

    }

    public TemperatureMetric(String deviceMAC, float temp){
        this.deviceMAC = deviceMAC;
        this.temp = temp;
        this.date = new Date();
    }

    public String getDeviceMAC() {
        return deviceMAC;
    }

    public float getTemp() {
        return temp;
    }

    public Date getDate() {
        return date;
    }


    @Override
    public String toString() {
        return "TemperatureMetric{" +
                "deviceMAC='" + deviceMAC + '\'' +
                ", temp=" + temp +
                ", date=" + date +
                '}';
    }
}
