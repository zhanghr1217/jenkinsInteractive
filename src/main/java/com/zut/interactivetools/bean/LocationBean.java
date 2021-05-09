package com.zut.interactivetools.bean;

/**
 * @program: interactivetools
 * @description: location
 * @author: zjj
 * @create: 2021-04-11 15:33
 **/
public class LocationBean {
    private Integer id;
    private String description;
    private Double lat;
    private Double lng;
    private Integer status;

    public LocationBean() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
