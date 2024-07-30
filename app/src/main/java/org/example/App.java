package org.example;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // 8888포트 사용, 대기 인원 총 2명.
        try (ServerSocket socket = new ServerSocket(8888, 2)) {
            System.out.println("가위 바위 보 경마 게임에 오신 것을 환영합니다!");
            System.out.println("각 플레이어가 자신의 말을 선택하고 경주를 시작합니다.\n");

            System.out.println("플레이어를 기다리는 중....");

            // 사용자 입장
            Socket s1 = socket.accept();
            System.out.println("첫 번째 멤버가 입장하였습니다. !");
            // 클라이언트 소켓을 닫기 위해 try-with-resources 사용
            PrintStream out = new PrintStream(s1.getOutputStream());
            Scanner in = new Scanner(s1.getInputStream());

            Socket s2 = socket.accept();
            System.out.println("두 번째 멤버가 입장하였습니다. !");
            PrintStream out2 = new PrintStream(s2.getOutputStream());
            Scanner in2 = new Scanner(s2.getInputStream());
            out.println("start");

            System.out.println("경기가 시작됩니다!");
            while (true) {
                // 키보드 입력을 받아서 서버1,2에게 전송한다.
                System.out.print("입력> ");
                String input = scanner.nextLine();
                out.println(input);
                out2.println(input);

                System.out.println("사용자2가 입력 중입니다.");

                // 서버1이 보낸 데이터를 수신한다.
                String str = in.nextLine();
                System.out.println(str);
                // 서버 2에게 전송
                out2.println(str);

                System.out.println("사용자3이 입력 중입니다.");

                // 서버2가 보낸 데이터를 수신한다.
                String str2 = in2.nextLine();
                System.out.println(str2);
                // 서버1에게 전송
                out.println(str2);
            }

        } catch (Exception e) {
            System.err.println("서버 소켓 처리 중 오류: " + e.getMessage());
        }

        System.out.println("서버 종료");
    }
}
