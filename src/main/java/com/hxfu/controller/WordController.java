package com.hxfu.controller;

import com.hxfu.entity.Word;
import com.hxfu.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/word")
public class WordController {

    @Autowired
    private WordService wordService;

    private int COUNT=10;

    @GetMapping("/getwords")
    public List<Word> listusers(@RequestParam("bookId") String bookId,@RequestParam("wordId") String wordId) {
        System.out.println("sss");
        return wordService.getWords(bookId, wordId);
    }
}
