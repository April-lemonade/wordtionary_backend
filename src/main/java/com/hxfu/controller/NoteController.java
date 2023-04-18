package com.hxfu.controller;

import com.hxfu.entity.Note;
import com.hxfu.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/note")
public class NoteController {
    @Autowired
    NoteService noteService;

    @PostMapping("/addnote")
    public int fileaddlist(@RequestParam(value = "WordId") int wordId,
                           @RequestParam(value = "openid") String openid,
                           @RequestParam(value = "content") String content) {
        return noteService.addNote(openid, wordId, content);
    }

    @GetMapping("/getnote")
    public List<Note> getNote(String openid, int wordId) {
        return noteService.getNote(openid, wordId);
    }
}
