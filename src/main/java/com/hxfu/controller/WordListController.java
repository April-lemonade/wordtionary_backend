package com.hxfu.controller;

import com.hxfu.entity.WordList;
import com.hxfu.mapper.WordListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wordlist")
public class WordListController {
    @Autowired
    private WordListMapper wordListMapper;

    @GetMapping("/getall")
    public List<WordList> listusers() {
        System.out.println("sss");
        return wordListMapper.getAll();
    }
}
