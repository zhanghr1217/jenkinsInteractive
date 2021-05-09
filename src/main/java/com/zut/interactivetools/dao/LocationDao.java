package com.zut.interactivetools.dao;

import com.zut.interactivetools.bean.LocationBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LocationDao {
    List<LocationBean> getAllLocations(@Param("status") Integer status);
}
