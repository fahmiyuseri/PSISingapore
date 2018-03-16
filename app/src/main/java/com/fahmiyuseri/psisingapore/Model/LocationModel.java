package com.fahmiyuseri.psisingapore.Model;

import java.util.List;

/**
 * Created by IRSB on 14/3/2018.
 */

public class LocationModel {
    private String name;
    private double latitude;
    private double longitude;
    private List<PSIModel> psiModels;
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    public List<PSIModel> getPsiModels() {
        return psiModels;
    }

    public void setPsiModels(List<PSIModel> psiModels) {
        this.psiModels = psiModels;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
