package com.hxfu.service.impl;

import com.hxfu.entity.Note;
import com.hxfu.mapper.NoteMapper;
import com.hxfu.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NoteServiceImpl implements NoteService {
    @Autowired
    NoteMapper noteMapper;

    @Override
    public int addNote(String openid, int wordId, String content) {
        if(noteMapper.getNote(openid, wordId).size()==0){
            noteMapper.addNote(openid, wordId, content);
        }else{
            noteMapper.updateNote(openid, wordId, content);
        }
        return 0;
    }

    @Override
    public List<Note> getNote(String sopenid, int wordId) {
        return noteMapper.getNote(sopenid, wordId);
    }
}
