package com.hxfu.service;

import com.hxfu.entity.WordList;

import java.util.List;

public interface WordListService {
    List<WordList> getadminAll();
    int change(String bookId, String openid);
    String getName(String bookId);
    List<WordList> getuserAll(String openid);
}
