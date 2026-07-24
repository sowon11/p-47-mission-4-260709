import com.ll.wiseSaying.App;
import com.ll.wiseSaying.global.AppContext;

import java.io.ByteArrayOutputStream;
import java.util.Scanner;

public class AppTestRunner {
    public static String run(String input){
        // input 끝에 '종료'가 없으면 자동으로 붙이기
        if(!input.trim().endsWith("종료")){
            input = input.stripTrailing() + "\n종료\n";
        }

        // 테스트용 가상 스캐너 생성
        Scanner scanner = TestUtil.genScanner(input);

        // AppContext의 스캐너를 테스트용 스캐너로 임시 교체
        AppContext.scanner = scanner;

        // 콘솔 출력 가로채기
        ByteArrayOutputStream outputStream = TestUtil.setOutToByteArray();

        // App 실행
        App app = new App(scanner);
        app.run();

        TestUtil.clearSetOutToByteArray(outputStream);
        // 출력 결과 문자열로 반환
        String out = outputStream.toString();

        // 테스트용 스캐너, 출력 스트림 원상복구
        try{
            TestUtil.clearSetOutToByteArray(outputStream);
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            AppContext.scanner = new Scanner(System.in);
        }

        return out;
    }
}
