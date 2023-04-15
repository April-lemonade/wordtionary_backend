package com.hxfu.mapper;

import com.hxfu.entity.Record;
import com.hxfu.entity.Statistics;

import java.util.Date;
import java.util.List;

public interface RecordMapper {
    int addRecord(String openid, int wordId, Date date, int familiar, int familiarCount, Date nextDate);

    List<Statistics> getAll(String openid);

    List<Record> getFamiliarCount(String openid, int wordId);
}
