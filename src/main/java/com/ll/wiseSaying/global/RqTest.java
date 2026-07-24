package com.ll.wiseSaying.global;

public class RqTest {
    public static void main(String[] args){
        testActionName();
        testGetParam();

        testGetParamEx();
        testGetParamEx2();
        testGetParamEx3();
        testGetParamAsInt();
    }

    public static void testGetParamAsInt(){
        Rq rq = new Rq("삭제?id=3");
        int id1 = rq.getParamAsInt("id", -1);
        System.out.println(id1);

        Rq rq2 = new Rq("삭제id=");
        int id2 = rq2.getParamAsInt("id", -1);
        System.out.println(id2);
    }

    public static void testGetParamEx3(){
        Rq rq = new Rq("목록?searchKeyword=&keyword=kkk");
        String searchKeyword = rq.getParam("searchKeyword", "");
        String keyword = rq.getParam("keyword", "");
        System.out.println(searchKeyword);
        System.out.println(keyword);
    }

    public static void testGetParamEx2(){
        Rq rq = new Rq("목록?searchKeyword=");
        String searchKeyword = rq.getParam("searchKeyword", "hello");
    }

    public static void testGetParamEx(){
        Rq rq = new Rq("목록?");
        String searchKeyword = rq.getParam("searchKeyword", "");
        System.out.println(searchKeyword);
    }

    public static void testGetParam(){
        Rq rq = new Rq("목록?searchKeyword=영광");
        String searchKeyword = rq.getParam("searchKeyword", "");
        System.out.println(searchKeyword);

        Rq rq2 = new Rq("목록?keywordType=content");
        String keywordType2 = rq2.getParam("keywordType", "");
        System.out.println(keywordType2);
    }

    public static void testActionName(){
        Rq rq1 = new Rq("삭제?id=1");
        String action = rq1.getActionName();
        System.out.println(action);

        Rq rq2 = new Rq("수정?id=1");
        String action2 = rq2.getActionName();
        System.out.println(action2);
    }
}
