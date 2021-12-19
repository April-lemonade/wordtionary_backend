package com.hxfu.mapper;

import com.hxfu.entity.Activity;

import java.util.List;


public interface ActivityMapper {
    List<Activity> getAll();
    int addActivity(Activity activity);
    int editActivity(Activity activity);
    int deleteActivity(int id);
    int getCounts(String color);
}
