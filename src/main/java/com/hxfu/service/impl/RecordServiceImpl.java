package com.hxfu.service.impl;

import com.hxfu.entity.Record;
import com.hxfu.entity.Statistics;
import com.hxfu.mapper.RecordMapper;
import com.hxfu.mapper.UserMapper;
import com.hxfu.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    RecordMapper recordMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public int addRecord(String openid, int wordId, int listId, int familiar) throws ParseException {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time = format.format(date);
        List<Record> records = recordMapper.getFamiliarCount(openid, wordId);
        String[] trace = userMapper.getFamiliar(openid).split(",");
        Date newDate;
        int newFamiliar = 0;
        if (records.size() == 0) { //第一次背
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            if (familiar == 2) {
                calendar.add(Calendar.DATE, 2);
                newFamiliar = 1;
            }
            if (familiar == 1)
                calendar.add(Calendar.DATE, 1);
            if (familiar == -1)
                newFamiliar = -1;
            newDate = calendar.getTime();
            recordMapper.addRecord(openid, wordId, listId, format.parse(time), familiar, newFamiliar, newDate);
        } else { //不是第一次背
            Record currentRecord = records.get(0);
            newFamiliar = currentRecord.getFamiliarCount() + 1;
            if (familiar == 2 && newFamiliar != 0 && newFamiliar < trace.length) {
                Date currentDate = currentRecord.getNextDate();
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(currentDate);
                // 把日期往后增加一天,整数  往后推,负数往前移动
                calendar.add(Calendar.DATE, Integer.parseInt(trace[newFamiliar - 1]));
                // 这个时间就是日期往后推一天的结果
                newDate = calendar.getTime();
                recordMapper.addRecord(openid, wordId, listId, format.parse(time), familiar, newFamiliar, newDate);
            }
            if (familiar == 2 && newFamiliar == 0) { //第一次熟悉

                Date currentDate = currentRecord.getNextDate();
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(new Date());
                System.out.println("是第一次熟悉熟悉熟悉");
                // 把日期往后增加一天,整数  往后推,负数往前移动
                calendar.add(Calendar.DATE, Integer.parseInt(trace[0]));
                // 这个时间就是日期往后推一天的结果
                newDate = calendar.getTime();
                recordMapper.addRecord(openid, wordId, listId, format.parse(time), familiar, 1, newDate);
            }
            if (familiar == 1) {
                Date currentDate = currentRecord.getNextDate();
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(currentDate);
                // 把日期往后增加一天,整数  往后推,负数往前移动
                calendar.add(Calendar.DATE, 1);
                // 这个时间就是日期往后推一天的结果
                newDate = calendar.getTime();
                recordMapper.addRecord(openid, wordId, listId, format.parse(time), familiar, newFamiliar, newDate);
            }
            if (familiar == -1) {
                recordMapper.addRecord(openid, wordId, listId, format.parse(time), -1, 0, date);
            }
        }
        //修改用户背单词记录
        int currentId = userMapper.getWordId(openid);
        int currentList = userMapper.getListId(openid);
        if (wordId > currentId && listId == currentList) //如果是用户没背过的单词，就更新单词背诵进度
            userMapper.updatewordid(openid, wordId);
        return 0;
    }

    @Override
    public List<Statistics> getAll(String openid) {
        List<Statistics> records = recordMapper.getAll(openid);
        System.out.println(records.get(0).getDate());
        for (Statistics item : records) {
            System.out.println(item.toString());
        }
        return records;
    }

}
