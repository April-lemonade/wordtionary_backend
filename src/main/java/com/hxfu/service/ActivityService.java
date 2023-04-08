package com.hxfu.service;

import com.hxfu.entity.Activity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2021-09-25
 */
public interface ActivityService {
    /*List<Activity> getAll();
    int addActivity(Activity activity);
    int editActivity(Activity activity);
    int deleteActivity(int id);
    List<Integer> getCounts();*/
    String findProduct(String code);
}
