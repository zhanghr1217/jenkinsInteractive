package com.zut.interactivetools.service;

import com.zut.interactivetools.bean.LocationBean;

import java.util.List;

public interface LocationService {
    List<LocationBean> getAllLocations(Integer status);

    double GetDistance(double lat1, double lng1, double lat2, double lng2);
}
