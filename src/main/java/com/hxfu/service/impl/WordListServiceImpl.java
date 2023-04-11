package com.hxfu.service.impl;

import com.hxfu.entity.Word;
import com.hxfu.entity.WordList;
import com.hxfu.mapper.WordListMapper;
import com.hxfu.mapper.WordMapper;
import com.hxfu.service.WordListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WordListServiceImpl implements WordListService {
    @Autowired
    private WordListMapper wordListMapper;
    @Autowired
    private WordMapper wordMapper;

    public List<WordList> getadminAll() {
        return wordListMapper.getadminAll();
    }

    @Override
    public int change(String bookId, String openid) {
        return wordListMapper.change(bookId, openid);
    }

    @Override
    public String getName(String bookId) {
        return wordListMapper.getName(bookId);
    }

    @Override
    public List<WordList> getuserAll(String openid) {
        return wordListMapper.getuserAll(openid);
    }

    @Override
    public int addList(String name, String word, String openid) {
        //新词书插入wordlist表
        wordListMapper.addList(name, openid);
        //新单词插入word表
        int listid = wordListMapper.findId(name);
        String[] words = word.split("\n");
        for (String i : words) {
            System.out.println(i);
            Word temp = new Word();
            temp.setWord(i);
            temp.setListid(listid);
            wordMapper.addWord(temp);
        }
        return 0;
    }
}
