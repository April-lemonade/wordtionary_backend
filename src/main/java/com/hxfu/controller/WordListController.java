package com.hxfu.controller;

import com.hxfu.entity.WordList;
import com.hxfu.service.WordListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wordlist")
public class WordListController {
    @Autowired
    private WordListService wordListService;

    @GetMapping("/getadmin")
    public List<WordList> listusers() {
        System.out.println("sss");
        return wordListService.getadminAll();
    }

    @GetMapping("/change")
    public int change(@RequestParam("bookId") String bookId, @RequestParam("openid") String openid) {
        System.out.println(bookId);
        return wordListService.change(bookId, openid);
    }

    @GetMapping("/getname")
    public String getName(@RequestParam("bookId") String bookId){
        return wordListService.getName(bookId);
    }

    @GetMapping("/getuser")
    public List<WordList> userbook(@RequestParam("openid") String openid) {
//        System.out.println("sss");
        return wordListService.getuserAll(openid);
    }
}
