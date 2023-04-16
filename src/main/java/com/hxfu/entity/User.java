package com.hxfu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;
    private String name;
    private String avatarUrl;
    private int bookId;
    private int wordId;
    private String account;
    private String pwd;
    private int dictionaryId;
    private String familiar;
    private int dailyCount;
}
