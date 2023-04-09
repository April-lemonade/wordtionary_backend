package com.hxfu.mapper;

import com.hxfu.entity.WordList;

import java.util.List;

public interface WordListMapper {
    List<WordList> getAll();
    int change(String bookId, String openid);
}
