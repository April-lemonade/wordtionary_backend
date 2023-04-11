package com.hxfu.mapper;

import java.util.Date;

public interface RecordMapper {
    int addRecord(String openid, int wordId, Date date, int familiar);
}
