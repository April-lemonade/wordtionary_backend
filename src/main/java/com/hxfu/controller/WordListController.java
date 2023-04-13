package com.hxfu.controller;

import com.hxfu.entity.WordList;
import com.hxfu.service.WordListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/wordlist")
public class WordListController {
    @Autowired
    private WordListService wordListService;

    @GetMapping("/getadmin")
    public List<WordList> listusers() {
        return wordListService.getadminAll();
    }

    @GetMapping("/change")
    public int change(@RequestParam("bookId") String bookId, @RequestParam("openid") String openid) {
//        System.out.println(bookId);
        return wordListService.change(bookId, openid);
    }

    @GetMapping("/getname")
    public String getName(@RequestParam("bookId") String bookId) {
        return wordListService.getName(bookId);
    }

    @GetMapping("/getuser")
    public List<WordList> userbook(@RequestParam("openid") String openid) {
        return wordListService.getuserAll(openid);
    }

    @PostMapping("/addlist")
    public int addlist(String name, String word, String openid) {
        return wordListService.addList(name, word, openid);
    }

    @PostMapping("/webaddlist")
    public int webaddlist(String name, String word, String openid) {
        return wordListService.webaddList(name, word, openid);
    }

    @PostMapping("/fileaddlist")
    public int fileaddlist(@RequestParam("excelFile") MultipartFile file,
                           @RequestParam(value = "name") String name,
                           @RequestParam(value = "openid") String openid) {

        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        wordListService.fileaddlist(file,name,openid);
//        return wordListService.webaddList(name,, openid);
        return 0;
    }
}
