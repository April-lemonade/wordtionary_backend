package com.hxfu.service;

import com.hxfu.entity.WordList;

import java.util.List;

public interface WordListService {
    List<WordList> getAll();
    int change(String bookId, String openid);
}
