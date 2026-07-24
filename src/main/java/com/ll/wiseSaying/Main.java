package com.ll.wiseSaying;

import com.ll.wiseSaying.global.AppContext;

public class Main {
    public static void main(String[] args){
        App wsApp = new App(AppContext.scanner);
        wsApp.run();
    }
}
