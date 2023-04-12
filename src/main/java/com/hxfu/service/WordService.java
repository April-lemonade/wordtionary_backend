package com.hxfu.service;

import com.hxfu.entity.Word;

import java.util.List;

public interface WordService {
    List<Word> getWords( String bookId, String wordId);
    List<Word> showWords(String bookId, String wordId);
}
