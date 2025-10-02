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
    private String title; //タイトル
    private String content; //メモの内容
    private LocalDateTime createdAt = LocalDateTime.now(); //作成日付

    //no-arg constructor
    public Memo() {}

    //引数ありconstructor
    public Memo(String title, String content, LocalDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }
}
