package com.hxfu.service;

import com.hxfu.entity.Note;

import java.util.List;

public interface NoteService {
    int addNote(String openid, int wordId, String content);

    List<Note> getNote(String openid, int wordId);
}
