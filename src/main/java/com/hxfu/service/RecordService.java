package com.hxfu.service;

import com.hxfu.entity.Statistics;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface RecordService {
    int addRecord(String openid, int wordId, int familiar) throws ParseException;
    List<Statistics> getAll(String openid);
}
