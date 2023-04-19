package com.hxfu.mapper;

import com.baomidou.mybatisplus.extension.api.R;
import com.hxfu.entity.Record;
import com.hxfu.entity.Statistics;

import java.util.Date;
import java.util.List;

public interface RecordMapper {
    int addRecord(String openid, int wordId, int listId, Date date, int familiar, int familiarCount, Date nextDate);

    List<Statistics> getAll(String openid);

    List<Record> getFamiliarCount(String openid, int wordId);

    List<Integer> getReview(String openid, String date);

    List<Integer> getRelearn(String date, String openid);

    int getFamiliar(String openid, int wordId, String date);

    List<Record> getTodayRecord(String openid, String date);

    List<Record> getLastWord(String openid, int listId);

    int getLearnedCount(String openid, int listId);

    int getAllRelearn(String openid, int listId, int allFamiliarCount);
}
