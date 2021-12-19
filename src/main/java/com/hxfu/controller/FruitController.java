package com.hxfu.controller;


import com.hxfu.entity.Activity;
import com.hxfu.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;


@RestController
@RequestMapping("/activity")
public class FruitController {

    @Autowired
    private ActivityService activityService;

    @GetMapping("/getall")
    public List<Activity> listusers() {
        return activityService.getAll();
    }


    @PostMapping("/add")
    public void addActivity(@RequestBody Activity activity) {
        activity.setTimestamp(new Timestamp(System.currentTimeMillis()).toString());
        System.out.println(activity.getContent());
        System.out.println("进来了");
//        Activity activity = new Activity(map.get("content").toString(), map.get("color").toString(), new Timestamp(System.currentTimeMillis()));
//        System.out.println(map.get("content"));
        activityService.addActivity(activity);
    }

    @PutMapping("/edit")
    public void editActivity(@RequestBody Activity activity) {

        System.out.println("进来了");
//        Activity activity = new Activity(Integer.parseInt(map.get("id").toString()), map.get("content").toString(), map.get("color").toString());
//        System.out.println(map.get("content"));
        activityService.editActivity(activity);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteActivity(@PathVariable Integer id) {
        System.out.println("准备删除");
//        Activity activity = new Activity(Integer.parseInt(map.get("id").toString()), map.get("content").toString(), map.get("color").toString(), new Timestamp(System.currentTimeMillis()));
        activityService.deleteActivity(id);
    }

    @GetMapping("/counts")
    public List<Integer> getCounts() {
        System.out.println("准备计数");
        return activityService.getCounts();
    }
}

