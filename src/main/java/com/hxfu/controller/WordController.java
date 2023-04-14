package com.hxfu.controller;

import com.alibaba.fastjson.JSONObject;
import com.hxfu.entity.Word;
import com.hxfu.service.WordService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/word")
public class WordController {

    @Autowired
    private WordService wordService;

    private int COUNT = 10;

    @GetMapping("/getwords")
    public List<Word> listusers(@RequestParam("bookId") String bookId, @RequestParam("wordId") String wordId, @RequestParam("dictionaryId") String dictionaryId) throws IOException {
        System.out.println("sss");
        return wordService.getWords(bookId, wordId,dictionaryId);
    }

    @GetMapping("/showwords")
    public List<Word> showWords(@RequestParam("bookId") String bookId, @RequestParam("wordId") String wordId) {
//        System.out.println("sss");
        return wordService.showWords(bookId, wordId);
    }

    /*@GetMapping("/word")
    public String Cambridge(String word) throws IOException {
        String url = "https://dictionary.cambridge.org/zhs/%E8%AF%8D%E5%85%B8/%E8%8B%B1%E8%AF%AD-%E6%B1%89%E8%AF%AD-%E7%AE%80%E4%BD%93/" + word;
        Document document = Jsoup.parse(new URL(url), 8000);
        Elements definitions = document.getElementsByClass("db");
        Elements deftrans = document.getElementsByClass("trans dtrans dtrans-se  break-cj");
        Elements examples = document.getElementsByClass("def-body ddef_b");
        ArrayList<String> def = iteratorget(definitions.listIterator());
        ArrayList<String> deftranslation = iteratorget(deftrans.listIterator());
//        ArrayList<String> examp = iteratorget(examples.listIterator());
        ArrayList<String> examp = deal(deftrans, examples);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("definition", def);
//        jsonObject.put("deftrans", deftranslation);
        jsonObject.put("examples", examp);
        return jsonObject.toString();
    }*/


}
