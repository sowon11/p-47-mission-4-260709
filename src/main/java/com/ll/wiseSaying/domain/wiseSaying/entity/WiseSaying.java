package com.ll.wiseSaying.domain.wiseSaying.entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
@NoArgsConstructor // 매개변수 없는 기본 생성자
@AllArgsConstructor // 모든 인스턴스 변수에 대한 매개변수를 받는 생성자

public class WiseSaying {
    private int id;
    private String content;
    private String author;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private static final DateTimeFormatter FORMATTER
            = DateTimeFormatter.ofPattern("yyyy.MM.dd.HH:mm");

    // 생성자
    public WiseSaying(String content, String author){
        this.content = content;
        this.author = author;
    }

    // id가 있나 확인
    public boolean isNew(){
        return id == 0;
    }

    // 작성일 가져오기
    public String getCreateDate(){
        if(createDate == null) return "";
        return createDate.format(FORMATTER);
    }

    // 수정일 가져오기
    public String getModifyDate(){
        if(modifyDate == null) return "";
        return modifyDate.format(FORMATTER);
    }
}
