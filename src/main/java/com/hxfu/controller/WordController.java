package com.hxfu.controller;

import com.hxfu.entity.Word;
import com.hxfu.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/word")
public class WordController {

    @Autowired
    private WordService wordService;

    @GetMapping("/getwords")
    public List<Word> listusers(String bookId, String wordId, String dictionaryId, String openid) throws IOException {
        return wordService.getWords(bookId, wordId, dictionaryId, openid);
    }

    @GetMapping("/getoneword")
    public List<Word> getOneWord(String bookId, String wordId, String dictionaryId,int dailyCount) throws IOException {
        return wordService.getOneWords(Integer.parseInt(bookId), Integer.parseInt(wordId), dictionaryId,dailyCount);
    }

    @GetMapping("/showwords")
    public List<Word> showWords(@RequestParam("bookId") String bookId, @RequestParam("wordId") String wordId) {
        return wordService.showWords(bookId, wordId);
    }

    @GetMapping("/getrelearn")
    public List<Word> listusers(String openid) {
//        System.out.println("sss");
        return wordService.getRelearn(openid);
    }

    @GetMapping("/searchword")
    public Word searchWord(String word, String dictionaryId) throws IOException {
        return wordService.searchWord(word, dictionaryId);
    }

    @GetMapping("/addword")
    public int addWord(String word, String listid) {
        return wordService.addWord(word, Integer.parseInt(listid));
    }

    @GetMapping("predict")
    public int[] getPredict(String openid) {
        return wordService.getPredict(openid);
    }
}
