/**
 * POJO for JSON objects
 * Name - Ruta Deshpande
 * Andrew id - rutasurd
 * Email - rutasurd@andrew.cmu.edu
 */

package cmu.ds.geolocationapp;

import com.google.gson.Gson;

public class LocationInfo {
    private String country_name;
    private String state_prov;
    private String city;
    private String zipcode;
    private String latitude;
    private String longitude;
    private String organization;

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getState_prov() {
        return state_prov;
    }

    public void setState_prov(String state_prov) {
        this.state_prov = state_prov;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
