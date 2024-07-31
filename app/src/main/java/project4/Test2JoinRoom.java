package project4;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Test2JoinRoom {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.print("접속할 주소를 입력해주세요 : ");
        String login = scanner.nextLine();

        try (Socket socket = new Socket(login, 8888);) {
            System.out.println("🐎묵찌빠 경마 게임에 오신 것을 환영합니다!🐎");
            System.out.println("각 플레이어가 자신의 말을 선택하고 경주를 시작합니다.\n");

            PrintStream out = new PrintStream(socket.getOutputStream());
            Scanner in = new Scanner(socket.getInputStream());
            //마지막 플레이어 입장했다는 신호 수신
            String str = in.nextLine();
            if (str.equals("wait")) {

                secondPlayer(out, in);
            } else {

                thirdPlayer(out, in);
            }
        } catch (Exception e) {
            System.err.println("서버 소켓 처리 중 오류: " + e.getMessage());
        }
    }

    public static void secondPlayer(PrintStream out, Scanner in) throws Exception {
        System.out.println("플레이어를 기다리는 중....");
        //마지막 플레이어 입장했다는 신호 수신
        String str = in.nextLine();
        System.out.println("경기가 시작됩니다!");
        while (true) {
            System.out.println("사용자1 이 입력 중입니다.");
            // 서버가 보낸 데이터를 수신한다.
            str = in.nextLine();
            System.out.println(str);
            // 키보드 입력을 받아서 서버에게 전송한다.
            System.out.print("말 이름을 입력하세요 (두 글자): ");
            String input = scanner.nextLine();
            out.println(input);

            System.out.println("사용자3 이 입력 중입니다.");

            // 서버가 보낸 데이터를 수신한다.
            str = in.nextLine();
            System.out.println(str);

            // 말 이름 출력
            for (int i = 0; i < 3; i++) {
                str = in.nextLine();
                System.out.println(str);
            }

        }
    }

    public static void thirdPlayer(PrintStream out, Scanner in) throws Exception {
        System.out.println("경기가 시작됩니다!");
        while (true) {
            System.out.println("사용자1 이 입력 중입니다.");
            // 서버가 보낸 데이터를 수신한다.
            String str = in.nextLine();
            System.out.println(str);

            System.out.println("사용자2 가 입력 중입니다.");

            // 서버가 보낸 데이터를 수신한다.
            str = in.nextLine();
            System.out.println(str);
            // 키보드 입력을 받아서 서버에게 전송한다.
            System.out.print("말 이름을 입력하세요 (두 글자): ");
            String input = scanner.nextLine();
            out.println(input);

            //말 이름 출력
            for (int i = 0; i < 3; i++) {
                str = in.nextLine();
                System.out.println(str);
            }
        }
    }
}

