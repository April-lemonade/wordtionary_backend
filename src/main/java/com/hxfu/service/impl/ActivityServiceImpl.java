package com.hxfu.service.impl;

import com.hxfu.entity.Activity;
import com.hxfu.mapper.ActivityMapper;
import com.hxfu.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-09-25
 */
@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityMapper activityMapper;


/*    @Override
    public List<Activity> getAll() {
        return activityMapper.getAll();
    }

    @Override
    public int addActivity(Activity activity) {
        return activityMapper.addActivity(activity);
    }

    @Override
    public int editActivity(Activity activity) {
        return activityMapper.editActivity(activity);
    }

    @Override
    public int deleteActivity(int id) {
        return activityMapper.deleteActivity(id);
    }

    @Override
    public List<Integer> getCounts() {
        List<Integer> data = new ArrayList<>();
        String[] colors = {"Blue", "Green", "Orange", "Red"};
        for (String color : colors) {
            data.add(activityMapper.getCounts(color));
        }
        return data;
    }*/

    @Override
    public String findProduct(String code) {
        return activityMapper.findProduct(code);
    }
}
