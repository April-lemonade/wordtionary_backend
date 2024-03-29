package com.hxfu.mapper;

import com.hxfu.entity.Word;

import java.util.List;

public interface WordMapper {
    List<Word> getWords(int bookId, int wordId, int dailyCount);

    List<Word> getOneWords(int bookId, int wordId);

    Word getReviewWords(int wordId);

    int addOxfordTranslation(String translation, int wordId);

    int addCambridgeTranslation(String translation, int wordId);

    int addWord(Word word);

    List<Word> showWords(int bookId, int wordId);

    int deleteWord(String bookId);

    int getCounts(String bookId);

    List<Word> searchWord(String word);

    int getLastId(int listId);
}
