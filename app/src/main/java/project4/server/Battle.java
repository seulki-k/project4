package project4.server;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class Battle {
    public static void battle(boolean f1, Scanner scanner, List<String> choices, boolean f2, PrintStream out, Scanner in, PrintStream out2, Scanner in2) {
        ob:
        for (int i = 0; i <= CreateRoom.NUM_PLAYERS; i++) {
            String choice;
            //1번 플레이어 묵찌빠 선택
            while (f1) {
                out.println(CreateRoom.horseNames.get(i) + "가 입력중입니다.");
                out2.println(CreateRoom.horseNames.get(i) + "가 입력중입니다.");
                System.out.print(CreateRoom.horseNames.get(i) + "의 선택은? (묵 / 찌 / 빠): ");
                choice = scanner.nextLine();
                if (choice.equals("묵") || choice.equals("찌") || choice.equals("빠")) {
                    choices.add(choice);
                    f1 = false;
                    continue ob;
                } else {
                    System.out.println("유효한 선택이 아닙니다. '묵', '찌', '빠' 중 하나를 입력하세요.");
                }
            }

            System.out.println(CreateRoom.horseNames.get(i) + "가 입력중입니다.");
            //2번 플레이어 묵찌빠 선택
            while (f2) {
                out2.println(CreateRoom.horseNames.get(i) + "가 입력중입니다.");
                out.println(CreateRoom.horseNames.get(i) + "의 선택은? (묵 / 찌 / 빠): ");
                String choice2 = in.nextLine(); // 1번이 보낸 값 수신
                if (choice2.equals("묵") || choice2.equals("찌") || choice2.equals("빠")) {
                    choices.add(choice2);
                    out.println("success");
                    f2 = false;
                    continue ob;
                } else {
                    out.println("유효한 선택이 아닙니다. '묵', '찌', '빠' 중 하나를 입력하세요.");
                }
            }
            //3번 플레이어 묵찌빠 선택
            while (true) {
                out.println(CreateRoom.horseNames.get(i) + "가 입력중입니다.");
                out2.println(CreateRoom.horseNames.get(i) + "의 선택은? (묵 / 찌 / 빠): ");
                String choice3 = in2.nextLine(); // 2번이 보낸 값 수신
                if (choice3.equals("묵") || choice3.equals("찌") || choice3.equals("빠")) {
                    choices.add(choice3);
                    out2.println("success");
                    break ob;
                } else {
                    out2.println("유효한 선택이 아닙니다. '묵', '찌', '빠' 중 하나를 입력하세요.");
                }
            }
        }//묵찌빠 저장 완료
    }
}
