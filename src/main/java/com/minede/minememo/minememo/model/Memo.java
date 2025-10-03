package com.minede.minememo.minememo.model;

import com.fasterxml.jackson.annotation.JsonTypeId;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Memo {

    //Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Private Values
    //メモ
    private String title;   //タイトル
    private String content; //メモの本文

    //日付
    private LocalDateTime createdAt = LocalDateTime.now();  //作成日付
    private int year;   //年
    private int month;  //月
    private int day;    //日

    //no-arg constructor
    public Memo() {}

    //引数ありconstructor
    public Memo(String title, String content, LocalDateTime createdAt,
                int year, int month, int day) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.year = createdAt.getYear();
        this.month = createdAt.getMonthValue();
        this.day = createdAt.getDayOfMonth();
    }
}
