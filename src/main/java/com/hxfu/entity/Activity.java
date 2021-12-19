package com.hxfu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 *
 * </p>
 *
 * @author admin
 * @since 2021-09-25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activity {

    private int id;
    private String content;
    private String color;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss",timezone = "Asia/Shanghai")
    private String timestamp;

    public Activity(String content, String color, String timestamp) {
        this.content = content;
        this.color = color;
        this.timestamp = timestamp;
    }

    public Activity(int id, String content, String color) {
        this.id = id;
        this.content = content;
        this.color = color;
    }
}
