package com.ll.wiseSaying.domain.wiseSaying.service;

import com.ll.wiseSaying.domain.wiseSaying.dto.PageDto;
import com.ll.wiseSaying.domain.wiseSaying.entity.WiseSaying;
import com.ll.wiseSaying.domain.wiseSaying.repository.WiseSayingRepository;
import com.ll.wiseSaying.global.AppContext;

import java.util.List;

public class WiseSayingService {

    WiseSayingRepository wsRepository = AppContext.wsRepository;

    // 명언 등록
    public WiseSaying regWiseSaying(String content, String author){
        WiseSaying ws = new WiseSaying(content, author);
        return wsRepository.save(ws);
    }

    // 명언 삭제
    public void delWiseSaying(int id){
        wsRepository.delete(id);
    }

    // 명언 수정
    public void modWiseSaying(WiseSaying ws, String newContent, String newAuthor){
        ws.setContent(newContent);
        ws.setAuthor(newAuthor);

        wsRepository.save(ws);
    }

    // 명언 리스트에 해당 명언 있는지 확인
    public WiseSaying findWiseSaying(int id){
        return wsRepository.findWiseSaying(id);
    }

    // 명언 리스트 비어 있는지 확인
    public boolean isWsListEmpty(){
        return wsRepository.isWsListEmpty();
    }

    public PageDto findAllIdDesc(String keywordType, String keyword, int pageSize, int page){

        List<WiseSaying> list = wsRepository.findAllIdDesc();

        if(keywordType.equals("content")){
            return wsRepository.findByContentContainingIdDesc(keyword, pageSize, page);
        } else{
            return wsRepository.findByAuthorContainingIdDesc(keyword, pageSize, page);
        }
    }
}
