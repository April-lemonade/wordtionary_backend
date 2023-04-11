package com.hxfu.service.impl;

import com.hxfu.mapper.RecordMapper;
import com.hxfu.mapper.UserMapper;
import com.hxfu.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    RecordMapper recordMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public int addRecord(String openid, int wordId, int familiar) throws ParseException {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yy-MM-dd");
        String time = format.format(date);
        //添加背单词记录
        recordMapper.addRecord(openid, wordId, format.parse(time), familiar);
        //修改用户背单词记录
        userMapper.updatewordid(openid, wordId);
        return 0;
    }
}
