package org.example;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class App3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try (Socket socket = new Socket("localhost", 8888);) {
            System.out.println("가위 바위 보 경마 게임에 오신 것을 환영합니다!");
            System.out.println("각 플레이어가 자신의 말을 선택하고 경주를 시작합니다.\n");
            System.out.println("경기가 시작됩니다!");
            PrintStream out = new PrintStream(socket.getOutputStream());
            Scanner in = new Scanner(socket.getInputStream());
            while (true) {

                System.out.println("사용자1이 입력 중입니다.");

                // 서버가 보낸 데이터를 수신한다.
                String str = in.nextLine();
                System.out.println(str);

                System.out.println("사용자2가 입력 중입니다.");

                // 서버가 보낸 데이터를 수신한다.
                str = in.nextLine();
                System.out.println(str);
                // 키보드 입력을 받아서 서버에게 전송한다.
                System.out.print("입력> ");
                String input = scanner.nextLine();
                out.println(input);
                if (input.equals("quit")) {
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("서버 소켓 처리 중 오류: " + e.getMessage());
        }
        scanner.close();
    }

}
