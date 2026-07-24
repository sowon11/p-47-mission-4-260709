package com.ll.wiseSaying.domain.wiseSaying.controller;

import com.ll.wiseSaying.domain.wiseSaying.dto.PageDto;
import com.ll.wiseSaying.domain.wiseSaying.service.WiseSayingService;
import com.ll.wiseSaying.domain.wiseSaying.entity.WiseSaying;
import com.ll.wiseSaying.global.AppContext;
import com.ll.wiseSaying.global.Rq;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WiseSayingController {

    WiseSayingService wsService = AppContext.wsService;

    private Scanner getScanner(){ // 최신 상태의 스캐너 가져오기
        return AppContext.scanner;
    }

    // 명언 등록 과정
    public void regAction(){

        System.out.print("명언 : ");
        String content = getScanner().nextLine();
        System.out.print("작가 : ");
        String author = getScanner().nextLine();

        // 명언 객체 생성
        WiseSaying ws = wsService.regWiseSaying(content, author);
        // 메시지 출력
        System.out.printf("%d번 명언이 등록되었습니다.\n", ws.getId());
    }

    // 명언 삭제 과정
    public void delAction(Rq rq){
        WiseSaying ws = getValidWiseSaying(rq);
        if(ws == null) return;

        wsService.delWiseSaying(ws.getId());
        System.out.println(ws.getId() + "번 명언이 삭제되었습니다.");
    }

    // 명언 수정 과정
    public void modAction(Rq rq){
        WiseSaying ws = getValidWiseSaying(rq);
        if(ws == null) return;

        System.out.println("명언(기존) : " + ws.getContent());
        System.out.print("명언(수정) : ");
        String newContent = getScanner().nextLine();

        System.out.println("작가(기존) : " + ws.getAuthor());
        System.out.print("작가(수정) : ");
        String newAuthor = getScanner().nextLine();

        wsService.modWiseSaying(ws, newContent, newAuthor); // 명언 갱신
        System.out.println(ws.getId() + "번 명언이 수정되었습니다.");
    }

    // 명언 리스트 비어 있는지 검사
    public boolean isListEmpty(){
        if(wsService.isWsListEmpty()){ // wsList가 비어 있는지 검사
            System.out.println("명언 목록이 없습니다.");
            return true;
        }
        return false;
    }

    // rq에서 id를 꺼내 명언 리스트에 있으면 해당 객체 반환
    public WiseSaying getValidWiseSaying(Rq rq){
        if(isListEmpty()) return null; // wsList 비어 있는지 검사

        int id = rq.getParamAsInt("id", 0); // rq에서 id 추출
        if(id == 0){
            System.out.println("잘못된 입력입니다.");
            return null;
        }

        WiseSaying ws = wsService.findWiseSaying(id); // wsList에서 명언 찾기
        if(ws == null){
            System.out.println(id + "번 명언은 존재하지 않습니다.");
        }
        return ws; // 명언 객체 리턴
    }

    // 명언 목록 출력
    public void showWiseSayingList(Rq rq){
        if(isListEmpty()) return; // 리스트 비어 있는지 검사

        String keywordType = rq.getParam("keywordType", "");
        String keyword = rq.getParam("keyword", "");

        int page = rq.getParamAsInt("page", 1);
        int pageSize = rq.getParamAsInt("pageSize", 5);

        PageDto pageDto = wsService.findAllIdDesc(keywordType, keyword, pageSize, page);
        List<WiseSaying> wsList = pageDto.getContent();

        System.out.println("번호 / 작가 / 명언 / 작성일 / 마지막 수정일");
        System.out.println("----------------------------------------------------------------------");

        wsList
                .stream()
                .forEach(ws -> System.out.printf("%d / %s / %s / %s / %s\n"
                        , ws.getId(), ws.getAuthor(), ws.getContent(),
                        ws.getCreateDate(), ws.getModifyDate()));

        System.out.println("----------------------------------------------------------------------");
        // 1 2 3 4 5 ... 마지막 페이지 -> 페이지 개수 알아야 함.
        // 페이지 개수 => 전체 개수 / 페이지 사이즈, ex) total 10개, size 5개 -> 2개의 페이지 개수

        int totalPageCnt = pageDto.getTotalPageCnt();
        int currentPageNo = pageDto.getPage();

        String pageMenu = IntStream.rangeClosed(1, totalPageCnt)
                .mapToObj(i -> i == currentPageNo ? "[%d]".formatted(i) : String.valueOf(i))
                .collect(Collectors.joining(" / "));

        System.out.println("페이지 : " + pageMenu);
    }
}
