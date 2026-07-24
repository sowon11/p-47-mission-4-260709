import java.io.*;
import java.util.Scanner;

public class TestUtil {
    private static PrintStream ORIGINAL_OUT = System.out;
    private static PrintStream CURRENT_OUT = System.out;

    // 테스트용 스캐너 생성
    public static Scanner genScanner(final String input) {
        final InputStream in = new ByteArrayInputStream(input.getBytes());

        return new Scanner(in);
    }

    // System.out의 출력을 스트림으로 받기
    public static ByteArrayOutputStream setOutToByteArray() {
        ORIGINAL_OUT = System.out;
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(output);
        System.setOut(printStream);
        CURRENT_OUT = printStream;

        return output;
    }

    // setOutToByteArray 함수의 사용을 완료한 후 정리하는 함수, 출력을 다시 정상화 하는 함수
    public static void clearSetOutToByteArray(final ByteArrayOutputStream output) {
        System.setOut(ORIGINAL_OUT);
        try {
            output.close();
        } catch (IOException e) {
            System.out.println("aaa");
            throw new RuntimeException(e);
        }
        CURRENT_OUT.close();
    }
}