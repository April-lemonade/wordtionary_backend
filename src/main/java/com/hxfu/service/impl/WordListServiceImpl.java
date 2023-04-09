package com.hxfu.service.impl;

import com.hxfu.entity.WordList;
import com.hxfu.mapper.WordListMapper;
import com.hxfu.service.WordListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordListServiceImpl implements WordListService {
    @Autowired
    private WordListMapper wordListMapper;

    public List<WordList> getAll() {
        return wordListMapper.getAll();
    }

    @Override
    public int change(String bookId, String openid) {
        return wordListMapper.change(bookId, openid);
    }
}
