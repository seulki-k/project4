package project4.server;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class SetName {
    //닉네임 설정
    public static void SetName(Scanner scanner, PrintStream out, PrintStream out2, List<String> horseNames, Scanner in, Scanner in2) {
        // 키보드 입력을 받아서 서버1,2에게 전송한다.
        System.out.print("말 이름을 입력하세요 (두 글자): ");
        String input = scanner.nextLine();
        out.println(input);
        out2.println(input);
        horseNames.add(input); // 1번 마 이름

        System.out.println("사용자2 가 입력 중입니다.");

        // 서버1이 보낸 데이터를 수신한다.
        String str = in.nextLine();
        System.out.println("2번 플레이어 : " + str);
        // 서버 2에게 전송
        out2.println(str);
        horseNames.add(str); // 2번 마 이름


        System.out.println("사용자3 이 입력 중입니다.");

        // 서버2가 보낸 데이터를 수신한다.
        String str2 = in2.nextLine();
        System.out.println("3번 플레이어 : " + str2);
        // 서버1에게 전송
        out.println(str2);
        horseNames.add(str2); // 3번 마 이름

        int index = 1;
        // 서버,1,2 에게 말 이름 전송
        for (String horseName : horseNames) {
            System.out.println((index) + "번째 플레이어 : " + horseName);
            out.println((index) + "번째 플레이어 : " + horseName);
            out2.println((index++) + "번째 플레이어 : " + horseName);
        }
    }
}
