package com.hxfu.controller;

import com.hxfu.entity.Statistics;
import com.hxfu.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/record")
public class RecordController {
    @Autowired
    RecordService recordService;

    @GetMapping("/addrecord")
    public int addRecord(@RequestParam("openid") String openid, @RequestParam("wordId") String wordId, @RequestParam("listId") String listId, @RequestParam("familiar") String familiar) throws ParseException {
        return recordService.addRecord(openid, Integer.parseInt(wordId), Integer.parseInt(listId), Integer.parseInt(familiar));
    }

    @GetMapping("/getstatisics")
    public List<Statistics> getStatisics(@RequestParam("openid") String openid) {
        return recordService.getAll(openid);
    }
}
