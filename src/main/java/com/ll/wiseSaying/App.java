package com.ll.wiseSaying;

import com.ll.wiseSaying.domain.wiseSaying.controller.WiseSayingController;
import com.ll.wiseSaying.global.AppContext;
import com.ll.wiseSaying.global.Rq;

import java.util.Scanner;

// 명언 게시판 클래스
public class App {

    Scanner scanner;
    WiseSayingController wsController = AppContext.wsController;

    public App(Scanner scanner){
        this.scanner = scanner;
    }

    public void run(){
        System.out.println("== 명언 앱 ==");

        String cmd;
        while(true){
            System.out.print("명령) ");
            cmd = scanner.nextLine();

            // cmd 파싱
            Rq rq = new Rq(cmd);
            String actionName = rq.getActionName();

            switch(actionName){
                case "등록":
                    wsController.regAction();
                    break;
                case "삭제":
                    wsController.delAction(rq);
                    break;
                case "수정":
                    wsController.modAction(rq);
                    break;
                case "목록":
                    wsController.showWiseSayingList(rq);
                    break;
                case "종료":
                    return;
                default:
                    System.out.println("명령어를 다시 입력해주세요.");
                    break;
            }
            System.out.println("----------------------------------------------------------------------");
        }
    }
}
