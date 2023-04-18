package com.hxfu.mapper;

import com.hxfu.entity.Note;

import java.util.List;

public interface NoteMapper {
    int addNote(String openid, int wordId, String content);

    List<Note> getNote(String openid, int wordId);

    int updateNote(String openid, int wordId, String content);
}
