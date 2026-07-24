package com.ll.wiseSaying.domain.wiseSaying.repository;

import com.ll.wiseSaying.domain.wiseSaying.dto.PageDto;
import com.ll.wiseSaying.domain.wiseSaying.entity.WiseSaying;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WiseSayingRepository {
    int lastId = 0;
    private final ArrayList<WiseSaying> wsList = new ArrayList<>();

    // 명언 리스트 비어 있는지 확인
    public boolean isWsListEmpty(){
        return wsList.isEmpty();
    }

    // 명언 리스트에 해당 명언 있는지 확인
    public WiseSaying findWiseSaying(int id){
        return wsList.stream()
                .filter((ws) -> ws.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // 명언 저장
    public WiseSaying save(WiseSaying ws){
        if(ws.isNew()){ // 명언 번호가 있는지 검사(이미 있는 명언인지)
            ws.setId(++lastId); // 새로 생성되는 명언에 다음 번호 설정
            ws.setCreateDate(LocalDateTime.now()); // 명언 생성일
            wsList.add(ws); // 리스트에 명언 추가
        }
        ws.setModifyDate(LocalDateTime.now()); // 수정일 갱신
        return ws;
    }

    // 명언 삭제
    public void delete(int id){
        wsList.removeIf(ws -> ws.getId() == id); // id를 가진 명언 있으면 삭제
    }

    public List<WiseSaying> findAllIdDesc(){
        return wsList.reversed();
    }

    public PageDto findByContentContainingIdDesc(String keyword, int pageSize, int page) {
        // 페이징 처리 된 결과 + 페이징 메타 정보
        // 현재 페이지 번호 + 페이지 사이즈 + 전체 페이지 개수 + 시작 페이지 번호 + 마지막 페이지 번호
        // 명언 목록 + 페이지 번호 + 전체 페이지 개수
        List<WiseSaying> result = wsList
                .reversed()
                .stream()
                .filter(
                        w -> w.getContent().contains(keyword)
                )
                .toList();
        return pageOf(result, page, pageSize);
    }

    public PageDto findByAuthorContainingIdDesc(String keyword, int pageSize, int page) {
        List<WiseSaying> result = wsList
                .reversed()
                .stream()
                .filter(
                        w -> w.getAuthor().contains(keyword)
                )
                .toList();
        return pageOf(result, page, pageSize);
    }

    private PageDto pageOf(List<WiseSaying> filteredContent, int pageNo, int pageSize) {

        List<WiseSaying> content = filteredContent.stream()
                .skip((pageNo-1) * pageSize)
                .limit(pageSize)
                .toList();

        int totalItems = filteredContent.size();

        return new PageDto(pageNo, pageSize, totalItems, content);
    }
}
