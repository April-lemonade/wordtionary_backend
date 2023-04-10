package com.hxfu.mapper;

import com.hxfu.entity.Word;

import java.util.List;

public interface WordMapper {
    List<Word> getWords(int bookId, int wordId);
    int addOxfordTranslation(String translation, int wordId);
}
