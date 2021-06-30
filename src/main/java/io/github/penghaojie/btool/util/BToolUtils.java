package io.github.penghaojie.btool.util;

import io.github.penghaojie.btool.exception.BToolException;
import io.github.penghaojie.btool.model.BuildingInfo;
import io.github.penghaojie.btool.service.BuildingInfoService;

public class BToolUtils {
    public static BuildingInfo getInfo() {
        Class<?> cls;
        try {
            cls = Class.forName("com.github.penghaojie.btool.service.BuildingInfoServiceImpl");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new BToolException("can't find BuildingInfoService implementation");
        }
        try {
            BuildingInfoService instance = (BuildingInfoService) cls.newInstance();
            return instance.getInfo();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
