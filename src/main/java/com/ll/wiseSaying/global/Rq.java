package com.ll.wiseSaying.global;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class Rq {

    String actionName;
    Map<String, String> paramMap = new HashMap<>();

    public Rq(String cmd){
        // 목록?keywordType=content&keyword=과거
        String[] cmdBits = cmd.split("\\?", 2);
        // 물음표 앞부분
        actionName = cmdBits[0].trim();
        // 물음표 뒷부분 - 없으면 빈 문자열
        String params = cmdBits.length > 1 ? cmdBits[1].trim() : "";

        if(params.isEmpty()) return;

        String[] paramBits = params.split("&");

        paramMap = Arrays.stream(paramBits)
                .map(part -> part.split("="))
                .filter(tokens -> tokens.length == 2)
                .collect(Collectors.toMap(
                        pair -> pair[0],
                        pair -> pair[1])
                );
    }

    public String getParam(String key, String defaultValue){
        // paramMap에 key가 있으면 해당 값 리턴, 없으면 defaultValue 리턴
        return paramMap.getOrDefault(key, defaultValue);
    }

    public int getParamAsInt(String key, int defaultValue){
        String result = getParam(key, "");

        // 파라미터가 없으면 경고 문장 없이 기본값 리턴
        if(result == null || result.isBlank()){
            return defaultValue;
        }

        // 숫자로 변환 시도
        try{
            return Integer.parseInt(result); // 문자열을 정수로 변환
        } catch(NumberFormatException e){
            System.out.println("잘못된 입력값을 넣어서 기본값으로 반환됩니다.");
            return defaultValue;
        }
    }
}
