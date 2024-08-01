package project4.client;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class JoinRoom {

    static Scanner scanner = new Scanner(System.in);

    public static void execute() {

//        System.out.print("접속할 주소를 입력해주세요 : ");
//        String login = scanner.nextLine();

        try (Socket socket = new Socket("localhost", 8888);) {
            System.out.println("🐎묵찌빠 경마 게임에 오신 것을 환영합니다!🐎");
            System.out.println("각 플레이어가 자신의 말을 선택하고 경주를 시작합니다.\n");

            PrintStream out = new PrintStream(socket.getOutputStream());
            Scanner in = new Scanner(socket.getInputStream());
            //마지막 플레이어 입장했다는 신호 수신
            String str = in.nextLine();
            if (str.equals("wait")) {
                SecondPlayer.execute(out, in);
            } else {
                ThirdPlayer.thirdPlayer(out, in);
            }
        } catch (Exception e) {
            System.err.println("서버 소켓 처리 중 오류: " + e.getMessage());
        }
    }



}

