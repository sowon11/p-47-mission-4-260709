package com.ll.wiseSaying;

public class ExceptionExam {
    public static void main(String[] args){

        int num = 10;
        long num2 = 20L;
        char c = 'A';

        try{
            int[] arr = new int[3];
            arr[2] = 10;
            String str = "1a";
            int result = Integer.parseInt(str);
            System.out.println(result + 2);
        } catch(ArrayIndexOutOfBoundsException e){
            System.out.println("배열 관련 예외 발생");
        } catch(NumberFormatException e){
            System.out.println("숫자 변환 관련 예외 발생");
        } catch(Exception e){
            System.out.println("기타 예외 발생");
        }
        System.out.println("hi hi");
        System.out.println("bye bye");
    }
}
