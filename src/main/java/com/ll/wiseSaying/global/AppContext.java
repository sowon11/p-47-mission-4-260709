package com.ll.wiseSaying.global;

import com.ll.wiseSaying.domain.system.controller.SystemController;
import com.ll.wiseSaying.domain.wiseSaying.controller.WiseSayingController;
import com.ll.wiseSaying.domain.wiseSaying.repository.WiseSayingRepository;
import com.ll.wiseSaying.domain.wiseSaying.service.WiseSayingService;

import java.util.Scanner;

public class AppContext {
    public static Scanner scanner = new Scanner(System.in);
    public static WiseSayingRepository wsRepository = new WiseSayingRepository();
    public static WiseSayingService wsService = new WiseSayingService();
    public static WiseSayingController wsController = new WiseSayingController();
    public static SystemController systemController = new SystemController();
}
