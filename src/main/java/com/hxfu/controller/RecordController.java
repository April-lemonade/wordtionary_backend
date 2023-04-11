package com.hxfu.controller;

import com.hxfu.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

@RestController
@RequestMapping("/record")
public class RecordController {
    @Autowired
    RecordService recordService;

    @GetMapping("/addrecord")
    public int addRecord(@RequestParam("openid") String openid, @RequestParam("wordId") String wordId, @RequestParam("familiar") String familiar) throws ParseException {
        return recordService.addRecord(openid, Integer.parseInt(wordId), Integer.parseInt(familiar));
    }
}
