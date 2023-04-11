package com.hxfu.service;

import java.text.ParseException;
import java.util.Date;

public interface RecordService {
    int addRecord(String openid, int wordId, int familiar) throws ParseException;
}
