package com.hxfu.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Word {
    private int id;
    private String word;
    private String brisound;
    private String unisound;
    private String[] audioFile;
    private String oxfordTranslations;
}
