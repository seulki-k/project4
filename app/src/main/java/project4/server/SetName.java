package project4.server;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class SetName {
    //닉네임 설정
    public static void SetName(Scanner scanner, PrintStream out, PrintStream out2, List<String> horseNames, Scanner in, Scanner in2) {
        // 키보드 입력을 받아서 서버1,2에게 전송한다.
        while (true) {
            System.out.print("말 이름을 입력하세요 (두 글자): ");
            String input = scanner.nextLine().trim();
            if (input.length() == 2 && isKorean(input)) {
                out.println(input);
                out2.println(input);
                horseNames.add(input); // 1번 마 이름
                break;
            } else {
                System.out.println("이름은 두글자 한글만 입력할 수 있습니다. 다시 시도하세요.");
            }
        }
//
        System.out.println("사용자2 가 입력 중입니다.");

        while (true) {
            String str = in.nextLine().trim();
            if (str.length() == 2 && isKorean(str)) {
                System.out.println("2번 플레이어 : " + str);
                out.println("ok");
                out2.println(str);
                horseNames.add(str); // 2번 마 이름
                break;
            } else {
                out.println("이름은 두글자 한글만 입력할 수 있습니다. 다시 시도하세요.");
            }
        }

        System.out.println("사용자3 이 입력 중입니다.");

        while (true) {
            String str2 = in2.nextLine().trim();
            if (str2.length() == 2 && isKorean(str2)) {
                System.out.println("3번 플레이어 : " + str2);
                out.println(str2);
                out2.println("ok");
                horseNames.add(str2); // 2번 마 이름
                break;
            } else {
                out2.println("이름은 두글자 한글만 입력할 수 있습니다. 다시 시도하세요.");
            }
        }

        int index = 1;
        // 서버,1,2 에게 말 이름 전송
        for (String horseName : horseNames) {
            System.out.println((index) + "번째 플레이어 : " + horseName);
            out.println((index) + "번째 플레이어 : " + horseName);
            out2.println((index++) + "번째 플레이어 : " + horseName);
        }
    }


    public static boolean isKorean(String text) {
        return text.matches("[가-힣]+");
    }

}
