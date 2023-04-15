package com.hxfu.mapper;

import com.hxfu.entity.WordList;

import java.util.List;

public interface WordListMapper {
    List<WordList> getadminAll();

    int change(String bookId, String openid);

    String getName(String bookId);

    List<WordList> getuserAll(String openid);

    int addList(String name, String openid);

    int findId(String name);

    int deleteList(String bookId);

    int changeListName(String name, String bookId);
}
