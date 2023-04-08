package com.hxfu.mapper;

import com.hxfu.entity.Word;

import java.util.List;

public interface WordMapper {
    List<Word> getWords(int count);
}
