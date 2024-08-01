package project4.client;

import project4.server.ClearConsole;

import java.io.PrintStream;
import java.util.Scanner;

public class SecondPlayer extends Player {
    public static void execute(PrintStream out, Scanner in) throws Exception {
        System.out.println("플레이어를 기다리는 중....");
        //마지막 플레이어 입장했다는 신호 수신
        String str = in.nextLine();
        System.out.println("사용자1 이 입력 중입니다.");
        // 서버가 보낸 데이터를 수신한다.
        str = in.nextLine();
        System.out.println("1번 플레이어 : " + str);
        // 키보드 입력을 받아서 서버에게 전송한다.
        System.out.print("말 이름을 입력하세요 (두 글자): ");
        String input = JoinRoom.scanner.nextLine();
        out.println(input);

        System.out.println("사용자3 이 입력 중입니다.");

        // 서버가 보낸 데이터를 수신한다.
        str = in.nextLine();
        System.out.println("3번 플레이어 : " + str);

        // 말 이름 출력
        for (int i = 0; i < 3; i++) {
            str = in.nextLine();
            System.out.println(str);
        }
        System.out.println("경기가 시작됩니다!");

        while (true) {
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println("\n✊✌️✋");

            //묵찌빠 값 전달
            while (true) {
                str = in.nextLine();
                System.out.print(str); //묵,찌, 빠 중에 골라라

                input = JoinRoom.scanner.nextLine();
                out.println(input); // 값 전달

                str = in.nextLine(); //저장 결과

                if (str.equals("success")) {
                    break;
                } else {
                    System.out.println(str); // 틀린 값으로 다시 입력하세요.
                }
            } //묵찌빠 완료

            System.out.println(in.nextLine()); //완료 수신

            threeSecondWait();

            System.out.println(in.nextLine()); //결과 출력

            burning(in); //버닝 유무 확인

            ClearConsole.clearConsole();


            positionHorse(in); // 말 위치 출력
        }
    }
}
