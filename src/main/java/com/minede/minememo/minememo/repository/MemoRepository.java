package com.minede.minememo.minememo.repository;

import com.minede.minememo.minememo.model.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;


public interface MemoRepository extends JpaRepository<Memo, Long>{
    //メモ
    List<Memo> findByTitle(String title);                                   //タイトルの完全一致
    List<Memo> findByContent(String content);                               //本文の完全一致
    //一部検索
    List<Memo> findByTitleContaining(String keyword);                       //タイトルの一部
    List<Memo> findByContentContaining(String keyword);                     //本文の一部
    //日付
    List<Memo> findByCreatedAt(LocalDateTime createdAt);                    //投稿日付の完全一致
    List<Memo> findByYear(int year);                                        //年
    List<Memo> findByYearAndMonth(int year, int month);                     //年・月
    List<Memo> findByYearAndMonthAndDay(int year, int month, int day);      //年・月・日
    List<Memo> findByCreatedAtAfter(LocalDateTime date);                    //ある日付以降
    List<Memo> findByCreatedAtBefore(LocalDateTime date);                   //ある日付以前
    List<Memo> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);  //ある期間
}
