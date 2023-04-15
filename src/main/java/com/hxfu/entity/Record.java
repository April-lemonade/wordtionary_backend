package com.hxfu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Record {
    private int id;
    private String openid;
    private String wordId;
    private Date date;
    private int familiar;
    private int familiarCount;
    private Date nextDate;
}
