import com.ll.wiseSaying.global.AppContext;
import com.ll.wiseSaying.domain.wiseSaying.controller.WiseSayingController;
import com.ll.wiseSaying.domain.wiseSaying.repository.WiseSayingRepository;
import com.ll.wiseSaying.domain.wiseSaying.service.WiseSayingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AppTest {

    @BeforeEach
    void setUp(){ // 테스트마다 객체 초기화
        AppContext.wsRepository = new WiseSayingRepository();
        AppContext.wsService = new WiseSayingService();
        AppContext.wsController = new WiseSayingController();
    }

    @Test
    @DisplayName("'== 명언 앱 ==' 출력")
    void t1() {

        String out = AppTestRunner.run("종료");
        assertThat(out).contains("== 명언 앱 ==");

    }

    @Test
    @DisplayName("등록")
    void t2(){
        String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                종료
                """);

        assertThat(out)
                .contains("명령) ")
                .contains("명언 : ")
                .contains("작가 : ");
    }

    @Test
    @DisplayName("등록 후 명언 번호 출력")
    void t3(){
        String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                종료
                """);
        assertThat(out).contains("1번 명언이 등록되었습니다.");
    }

    @Test
    @DisplayName("등록할 때마다 명언 번호 증가")
    void t4(){
        String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                종료
                """);
        assertThat(out)
                .contains("1번 명언이 등록되었습니다.")
                .contains("2번 명언이 등록되었습니다.");
    }

    @Test
    @DisplayName("목록")
    void t5(){
        String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록
                종료
                """);
        assertThat(out)
                .contains("번호 / 작가 / 명언 / 작성일 / 마지막 수정일")
                .contains("----------------------------------------------------------------------")
                .contains("2 / 작자미상 / 과거에 집착하지 마라.")
                .contains("1 / 작자미상 / 현재를 사랑하라.");
    }

    @Test
    @DisplayName("명언 삭제")
    void t6(){
        String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                삭제?id=1
                목록
                종료
                """);
        assertThat(out)
                .contains("1번 명언이 삭제되었습니다.")
                .contains("2 / 작자미상 / 과거에 집착하지 마라.")
                .doesNotContain("1 / 작자미상 / 현재를 사랑하라.");
    }

    @Test
    @DisplayName("존재하지 않는 명언 삭제 예외 처리")
    void t7(){
        String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록
                삭제?id=1
                삭제?id=1
                종료
                """);
        assertThat(out).contains("1번 명언은 존재하지 않습니다.");
    }

    @Test
    @DisplayName("없는 명언 수정 예외 처리")
    void t8(){
        String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                수정?id=3
                종료
                """);
        assertThat(out).contains("3번 명언은 존재하지 않습니다.");
    }

    @Test
    @DisplayName("명언 수정")
    void t9(){
        String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                수정?id=2
                현재와 자신을 사랑하라.
                홍길동
                목록
                종료
                """);
        assertThat(out)
                .doesNotContain("2 / 작자미상 / 과거에 집착하지 마라.")
                .contains("2 / 홍길동 / 현재와 자신을 사랑하라.")
                .contains("명언(기존) : 과거에 집착하지 마라.")
                .contains("작가(기존) : 작자미상");
    }

    @Test
    @DisplayName("목록?keywordType=content&keyword=과거")
    void t10(){
        String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록?keywordType=content&keyword=과거
                종료
                """);
        assertThat(out)
                .doesNotContain("1 / 작자미상 / 현재를 사랑하라.")
                .contains("2 / 작자미상 / 과거에 집착하지 마라.");
    }

    @Test
    @DisplayName("목록?keywordType=author&keyword=과거")
    void t11() {
        String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록?keywordType=author&keyword=과거
                종료
                """);

        assertThat(out)
                .doesNotContain("1 / 작자미상 / 현재를 사랑하라.")
                .doesNotContain("2 / 작자미상 / 과거에 집착하지 마라.");
    }

    @Test
    @DisplayName("목록: 한 페이지에 최신 명언 5개 출력")
    void t12() throws IOException {

        String input = IntStream.rangeClosed(1, 10)
                .mapToObj(num -> """
                        등록
                        명언 %d
                        작가 %d
                        """.formatted(num, num))
                .collect(Collectors.joining(""));

        input += "목록\n종료\n";

        String out = AppTestRunner.run(input);

        System.out.println(out);
        assertThat(out)
                .contains("10 / 작가 10 / 명언 10")
                .contains("9 / 작가 9 / 명언 9")
                .contains("8 / 작가 8 / 명언 8")
                .contains("7 / 작가 7 / 명언 7")
                .contains("6 / 작가 6 / 명언 6")
                .doesNotContain("5 / 작가 5 / 명언 5")
                .doesNotContain("4 / 작가 4 / 명언 4")
                .doesNotContain("3 / 작가 3 / 명언 3")
                .doesNotContain("2 / 작가 2 / 명언 2")
                .doesNotContain("1 / 작가 1 / 명언 1");
    }

    @Test
    @DisplayName("목록: 사용자가 페이지당 개수 선택 가능하도록. 목록?pageSize=5")
    void t13() throws IOException {

        String input = IntStream.rangeClosed(1, 10)
                .mapToObj(num -> """
                        등록
                        명언 %d
                        작가 %d
                        """.formatted(num, num))
                .collect(Collectors.joining(""));

        input += "목록?pageSize=3\n종료\n";

        String out = AppTestRunner.run(input);

        System.out.println(out);
        assertThat(out)
                .contains("10 / 작가 10 / 명언 10")
                .contains("9 / 작가 9 / 명언 9")
                .contains("8 / 작가 8 / 명언 8")
                .doesNotContain("7 / 작가 7 / 명언 7")
                .doesNotContain("6 / 작가 6 / 명언 6")
                .doesNotContain("5 / 작가 5 / 명언 5")
                .doesNotContain("4 / 작가 4 / 명언 4")
                .doesNotContain("3 / 작가 3 / 명언 3")
                .doesNotContain("2 / 작가 2 / 명언 2")
                .doesNotContain("1 / 작가 1 / 명언 1");
    }

    @Test
    @DisplayName("목록?page=2")
    void t14() {

        String input = IntStream.rangeClosed(1, 10)
                .mapToObj(num -> """
                        등록
                        명언 %d
                        작가 %d
                        """.formatted(num, num))
                .collect(Collectors.joining(""));

        input += "목록?page=2\n종료\n";

        String out = AppTestRunner.run(input);

        assertThat(out)
                .doesNotContain("10 / 작가 10 / 명언 10")
                .doesNotContain("9 / 작가 9 / 명언 9")
                .doesNotContain("8 / 작가 8 / 명언 8")
                .doesNotContain("7 / 작가 7 / 명언 7")
                .doesNotContain("6 / 작가 6 / 명언 6")
                .contains("5 / 작가 5 / 명언 5")
                .contains("4 / 작가 4 / 명언 4")
                .contains("3 / 작가 3 / 명언 3")
                .contains("2 / 작가 2 / 명언 2")
                .contains("1 / 작가 1 / 명언 1");
    }

    @Test
    @DisplayName("목록?page=2, 페이지 메뉴 출력")
    void t15() {

        String input = IntStream.rangeClosed(1, 10)
                .mapToObj(num -> """
                        등록
                        명언 %d
                        작가 %d
                        """.formatted(num, num))
                .collect(Collectors.joining(""));

        input += "목록?page=2\n종료\n";

        String out = AppTestRunner.run(input);

        assertThat(out)
                .doesNotContain("10 / 작가 10 / 명언 10")
                .doesNotContain("9 / 작가 9 / 명언 9")
                .doesNotContain("8 / 작가 8 / 명언 8")
                .doesNotContain("7 / 작가 7 / 명언 7")
                .doesNotContain("6 / 작가 6 / 명언 6")
                .contains("5 / 작가 5 / 명언 5")
                .contains("4 / 작가 4 / 명언 4")
                .contains("3 / 작가 3 / 명언 3")
                .contains("2 / 작가 2 / 명언 2")
                .contains("1 / 작가 1 / 명언 1")
                .contains("페이지 : 1 / [2]");
    }

    @Test
    @DisplayName("목록?page=1, 페이지 메뉴 출력")
    void t16() {

        String input = IntStream.rangeClosed(1, 10)
                .mapToObj(num -> """
                        등록
                        명언 %d
                        작가 %d
                        """.formatted(num, num))
                .collect(Collectors.joining(""));

        input += "목록?page=1\n종료\n";

        String out = AppTestRunner.run(input);

        assertThat(out)
                .contains("10 / 작가 10 / 명언 10")
                .contains("9 / 작가 9 / 명언 9")
                .contains("8 / 작가 8 / 명언 8")
                .contains("7 / 작가 7 / 명언 7")
                .contains("6 / 작가 6 / 명언 6")
                .doesNotContain("5 / 작가 5 / 명언 5")
                .doesNotContain("4 / 작가 4 / 명언 4")
                .doesNotContain("3 / 작가 3 / 명언 3")
                .doesNotContain("2 / 작가 2 / 명언 2")
                .doesNotContain("1 / 작가 1 / 명언 1")
                .contains("페이지 : [1] / 2");
    }
}