package org.example;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class App3 {
    public static void main(String[] args) throws Exception {
        System.out.println("클라이언트 실행!");

        Scanner scanner = new Scanner(System.in);

        Socket socket = new Socket("localhost", 8888);

        PrintStream out = new PrintStream(socket.getOutputStream());
        Scanner in = new Scanner(socket.getInputStream());

        while (true) {
            // 서버가 보낸 데이터를 수신한다.
            String str = in.nextLine();
            System.out.println(str);

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

        in.close();
        out.close();
        socket.close();
        scanner.close();
    }

}
