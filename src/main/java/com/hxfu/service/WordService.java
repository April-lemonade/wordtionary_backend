package com.hxfu.service;

import com.hxfu.entity.Word;

import java.io.IOException;
import java.util.List;

public interface WordService {
    List<Word> getWords(String bookId, String wordId, String dictionaryId, String openid) throws IOException;

    List<Word> showWords(String bookId, String wordId);

    List<Word> getRelearn(String openid);
}
