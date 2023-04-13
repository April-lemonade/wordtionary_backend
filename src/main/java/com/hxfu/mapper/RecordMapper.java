package com.hxfu.mapper;

import com.hxfu.entity.Statistics;

import java.util.Date;
import java.util.List;

public interface RecordMapper {
    int addRecord(String openid, int wordId, Date date, int familiar);
    List<Statistics> getAll(String openid);
}
