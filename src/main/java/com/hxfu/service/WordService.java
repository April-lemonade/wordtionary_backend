package com.hxfu.service;

import com.hxfu.entity.Word;

import java.io.IOException;
import java.util.List;

public interface WordService {
    List<Word> getWords(String bookId, String wordId, String dictionaryId, String openid) throws IOException;

    List<Word> getOneWords(int bookId, int wordId, String dictionaryId, int dailyCount) throws IOException;

    List<Word> showWords(String bookId, String wordId);

    List<Word> getRelearn(String openid);

    Word searchWord(String word, String dictionaryId) throws IOException;

    int addWord(String name, int listId);

    int[] getPredict(String openid);
}
