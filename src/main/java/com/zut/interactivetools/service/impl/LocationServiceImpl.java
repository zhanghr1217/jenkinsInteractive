package com.zut.interactivetools.service.impl;

import com.zut.interactivetools.bean.LocationBean;
import com.zut.interactivetools.dao.LocationDao;
import com.zut.interactivetools.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @program: interactivetools
 * @description:
 * @author: zjj
 * @create: 2021-04-11 15:39
 **/

@Service
public class LocationServiceImpl implements LocationService {

    private final static double EARTH_RADIUS = 6378137;//地球半径

    private double rad(double d) {
        return d * Math.PI / 180.0;
    }


    @Autowired
    LocationDao locationDao;

    @Override
    public List<LocationBean> getAllLocations(Integer status) {
        return locationDao.getAllLocations(status);
    }



    /**
     * 计算国内坐标系两点间距离 （高德地图）
     *
     * @return double 距离 单位公里,精确到米
     */
    @Override
    public double GetDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = new BigDecimal(s).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        return s;
    }

}
