package org.example;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        // 8888포트 사용, 대기 인원 총 2명.
        try (ServerSocket socket = new ServerSocket(8888, 2)) {
            System.out.println("플레이어를 기다리는 중....");

                // 사용자 입장
                Socket s1 = socket.accept();
                System.out.println("대기 중인 사용자 중 첫 번째 멤버가 입장하였습니다. !");
                // 클라이언트 소켓을 닫기 위해 try-with-resources 사용

                Socket s2 = socket.accept();
                System.out.println("대기 중인 사용자 중 두 번째 멤버가 입장하였습니다. !");

            while (true) {
                PrintStream out = new PrintStream(s1.getOutputStream());
                Scanner in = new Scanner(s1.getInputStream());

                PrintStream out2 = new PrintStream(s2.getOutputStream());
                Scanner in2 = new Scanner(s2.getInputStream());

                // 키보드 입력을 받아서 서버1,2에게 전송한다.
                System.out.print("입력> ");
                String input = scanner.nextLine();
                out.println(input);
                out2.println(input);

                // 서버1이 보낸 데이터를 수신한다.
                String str = in.nextLine();
                System.out.println(str);
                // 서버 2에게 전송
                out2.println(str);

                // 서버2가 보낸 데이터를 수신한다.
                String str2 = in2.nextLine();
                System.out.println(str2);
                // 서버1에게 전송
                out.printf(str2);

            }

        } catch (IOException e) {
            System.err.println("서버 소켓 처리 중 오류: " + e.getMessage());
        }

        System.out.println("서버 종료");
    }
}
